package com.astryxion.emv;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private static final double CLUSTER_RADIUS = 16.0;
    private static final Map<ILivingEntityData, Integer> PACK_VARIANTS = new IdentityHashMap<>();

    public static final class VariantGroupData implements ILivingEntityData {
        public final int variant;

        public VariantGroupData(int variant) {
            this.variant = variant;
        }
    }

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
        if (entity instanceof ChickenEntity) {
            return new Rule(8);
        }
        if (entity instanceof CowEntity) {
            return new Rule(10);
        }
        if (entity instanceof CatEntity) {
            return new Rule(6);
        }
        if (entity instanceof PigEntity) {
            return new Rule(6);
        }
        if (entity instanceof SheepEntity) {
            return new Rule(7);
        }
        if (entity instanceof WolfEntity) {
            return new Rule(7);
        }
        if (entity instanceof SkeletonEntity) {
            return new Rule(5);
        }
        if (entity instanceof SpiderEntity && !(entity instanceof CaveSpiderEntity)) {
            return new Rule(5);
        }
        if (entity instanceof ZombieEntity && !(entity instanceof HuskEntity)) {
            return new Rule(9);
        }
        return null;
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide || !(event.getWorld() instanceof ServerWorld)) {
            return;
        }
        onEntityLoad(event.getEntity(), (ServerWorld) event.getWorld());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            endServerTick();
        }
    }

    public static void onEntityLoad(Entity entity, ServerWorld world) {
        Rule rule = ruleFor(entity);
        if (rule == null || Registration.has(entity)) {
            return;
        }

        Registration.set(entity, pickSpawnVariant(entity, rule));
    }

    public static ILivingEntityData onFinalizeSpawn(MobEntity mob, SpawnReason reason, ILivingEntityData groupData) {
        if (mob.level.isClientSide) {
            return groupData;
        }

        Rule rule = ruleFor(mob);
        if (rule == null || !Config.isUniform() || Registration.has(mob)) {
            return groupData;
        }

        if (groupData instanceof VariantGroupData) {
            Registration.set(mob, ((VariantGroupData) groupData).variant);
            return groupData;
        }

        if (groupData != null) {
            Integer packVariant = PACK_VARIANTS.get(groupData);
            if (packVariant != null) {
                Registration.set(mob, packVariant);
                return groupData;
            }
        }

        int variant = mob.getRandom().nextInt(rule.bound());
        Registration.set(mob, variant);
        if (groupData == null) {
            return new VariantGroupData(variant);
        }

        PACK_VARIANTS.put(groupData, variant);
        return groupData;
    }

    public static void endServerTick() {
        PACK_VARIANTS.clear();
    }

    private static int pickSpawnVariant(Entity entity, Rule rule) {
        if (Config.isUniform()) {
            Integer nearby = findNearbyVariant(entity);
            if (nearby != null) {
                return nearby;
            }
        }
        return ((MobEntity) entity).getRandom().nextInt(rule.bound());
    }

    private static Integer findNearbyVariant(Entity entity) {
        World world = entity.level;

        AxisAlignedBB box = entity.getBoundingBox().inflate(CLUSTER_RADIUS);
        List<Entity> nearby = world.getEntities(entity, box, other -> other.getType() == entity.getType() && Registration.has(other));
        if (nearby.isEmpty()) {
            return null;
        }

        Entity closest = nearby.get(0);
        double best = entity.distanceToSqr(closest);
        for (int i = 1; i < nearby.size(); i++) {
            Entity other = nearby.get(i);
            double distance = entity.distanceToSqr(other);
            if (distance < best) {
                best = distance;
                closest = other;
            }
        }
        return Registration.get(closest);
    }
}
