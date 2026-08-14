package com.astryxion.emv;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityHusk;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        if (entity instanceof EntitySkeleton) {
            return new Rule(5);
        }
        if (entity instanceof EntitySpider && !(entity instanceof EntityCaveSpider)) {
            return new Rule(5);
        }
        if (entity instanceof EntityZombie && !(entity instanceof EntityHusk) && !(entity instanceof EntityPigZombie)) {
            return new Rule(9);
        }
        return null;
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (world == null || world.isRemote) {
            return;
        }
        onEntityLoad(event.getEntity());
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntityPlayer().world.isRemote || !(event.getEntityPlayer() instanceof EntityPlayerMP)) {
            return;
        }
        EmvNetwork.syncTo(event.getTarget(), (EntityPlayerMP) event.getEntityPlayer());
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

    private static Integer findNearbyVariant(Entity entity) {
        World world = entity.world;
        AxisAlignedBB box = entity.getEntityBoundingBox().grow(CLUSTER_RADIUS);
        List<Entity> nearby = world.getEntitiesInAABBexcluding(entity, box, other ->
            other != null && other.getClass() == entity.getClass() && Registration.has(other)
        );
        if (nearby.isEmpty()) {
            return null;
        }

        Entity closest = nearby.get(0);
        double best = entity.getDistanceSq(closest);
        for (int i = 1; i < nearby.size(); i++) {
            Entity other = nearby.get(i);
            double distance = entity.getDistanceSq(other);
            if (distance < best) {
                best = distance;
                closest = other;
            }
        }
        return Registration.get(closest);
    }
}
