package com.astryxion.emv;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;

public class EventHandler {
    private static final double CLUSTER_RADIUS = 16.0;

    private static final class Rule {
        private final int bound;

        private Rule(int bound) {
            this.bound = bound;
        }

        int bound() {
            return bound;
        }
    }

    private static Rule ruleFor(Entity entity) {
        if (entity instanceof EntityChicken) {
            return new Rule(8);
        }
        if (entity instanceof EntityCow) {
            return new Rule(10);
        }
        if (entity instanceof EntityOcelot) {
            return new Rule(6);
        }
        if (entity instanceof EntityPig) {
            return new Rule(6);
        }
        if (entity instanceof EntitySheep) {
            return new Rule(7);
        }
        if (entity instanceof EntityWolf) {
            return new Rule(7);
        }
        if (entity instanceof EntitySkeleton && ((EntitySkeleton) entity).getSkeletonType() == 0) {
            return new Rule(5);
        }
        if (entity instanceof EntitySpider && !(entity instanceof EntityCaveSpider)) {
            return new Rule(5);
        }
        if (entity instanceof EntityZombie && !(entity instanceof EntityPigZombie)) {
            return new Rule(9);
        }
        return null;
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        Entity entity = event.entity;
        World world = entity == null ? null : entity.worldObj;
        if (world == null || world.isRemote) {
            return;
        }
        onEntityLoad(entity);
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.entityPlayer.worldObj.isRemote || !(event.entityPlayer instanceof EntityPlayerMP)) {
            return;
        }
        EmvNetwork.syncTo(event.target, (EntityPlayerMP) event.entityPlayer);
    }

    public static void onEntityLoad(Entity entity) {
        Rule rule = ruleFor(entity);
        if (rule == null || Registration.has(entity)) {
            return;
        }

        Registration.set(entity, pickSpawnVariant(entity, rule));
    }

    private static int pickSpawnVariant(Entity entity, Rule rule) {
        if (Config.isUniform()) {
            Integer nearby = findNearbyVariant(entity);
            if (nearby != null) {
                return nearby;
            }
        }
        return ((EntityLiving) entity).getRNG().nextInt(rule.bound());
    }

    @SuppressWarnings("unchecked")
    private static Integer findNearbyVariant(Entity entity) {
        World world = entity.worldObj;
        AxisAlignedBB box = entity.boundingBox.expand(CLUSTER_RADIUS, CLUSTER_RADIUS, CLUSTER_RADIUS);
        List<Entity> nearby = world.getEntitiesWithinAABB(entity.getClass(), box);
        Entity closest = null;
        double best = Double.MAX_VALUE;
        for (Entity other : nearby) {
            if (other == entity || !Registration.has(other)) {
                continue;
            }
            double distance = entity.getDistanceSqToEntity(other);
            if (distance < best) {
                best = distance;
                closest = other;
            }
        }
        return closest == null ? null : Registration.get(closest);
    }
}
