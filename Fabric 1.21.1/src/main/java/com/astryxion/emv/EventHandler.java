package com.astryxion.emv;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private static final double CLUSTER_RADIUS = 16.0;
    private static final Map<SpawnGroupData, Integer> PACK_VARIANTS = new IdentityHashMap<>();

    public static final class VariantGroupData implements SpawnGroupData {
        public final int variant;

        public VariantGroupData(int variant) {
            this.variant = variant;
        }
    }

    private record Rule(AttachmentType<Integer> attachment, int bound) {}

    private static Rule ruleFor(Entity entity) {
        if (entity instanceof Chicken) {
            return new Rule(Registration.CHICKEN_VARIANT, 8);
        }
        if (entity instanceof Cow) {
            return new Rule(Registration.COW_VARIANT, 10);
        }
        if (entity instanceof Cat) {
            return new Rule(Registration.CAT_VARIANT, 6);
        }
        if (entity instanceof Pig) {
            return new Rule(Registration.PIG_VARIANT, 6);
        }
        if (entity instanceof Sheep) {
            return new Rule(Registration.SHEEP_VARIANT, 7);
        }
        if (entity instanceof Wolf) {
            return new Rule(Registration.WOLF_VARIANT, 7);
        }
        if (entity instanceof Skeleton) {
            return new Rule(Registration.SKELETON_VARIANT, 5);
        }
        if (entity instanceof Spider spider && !(spider instanceof CaveSpider)) {
            return new Rule(Registration.SPIDER_VARIANT, 5);
        }
        if (entity instanceof Zombie zombie && !(zombie instanceof Husk)) {
            return new Rule(Registration.ZOMBIE_VARIANT, 9);
        }
        return null;
    }

    public static void onEntityLoad(Entity entity, ServerLevel level) {
        Rule rule = ruleFor(entity);
        if (rule == null || entity.hasAttached(rule.attachment())) {
            return;
        }

        entity.setAttached(rule.attachment(), pickSpawnVariant(entity, rule));
    }

    public static SpawnGroupData onFinalizeSpawn(Mob mob, MobSpawnType reason, SpawnGroupData groupData) {
        if (mob.level().isClientSide()) {
            return groupData;
        }

        Rule rule = ruleFor(mob);
        if (rule == null || !Config.isUniform() || mob.hasAttached(rule.attachment())) {
            return groupData;
        }

        if (groupData instanceof VariantGroupData pack) {
            mob.setAttached(rule.attachment(), pack.variant);
            return groupData;
        }

        if (groupData != null) {
            Integer packVariant = PACK_VARIANTS.get(groupData);
            if (packVariant != null) {
                mob.setAttached(rule.attachment(), packVariant);
                return groupData;
            }
        }

        int variant = mob.getRandom().nextInt(rule.bound());
        mob.setAttached(rule.attachment(), variant);
        if (groupData == null) {
            return new VariantGroupData(variant);
        }

        PACK_VARIANTS.put(groupData, variant);
        return groupData;
    }

    public static void endServerTick() {
        PACK_VARIANTS.clear();
        Config.reloadCommonIfChanged();
    }

    private static int pickSpawnVariant(Entity entity, Rule rule) {
        if (Config.isUniform()) {
            Integer nearby = findNearbyVariant(entity, rule.attachment());
            if (nearby != null) {
                return nearby;
            }
        }
        return entity.getRandom().nextInt(rule.bound());
    }

    private static Integer findNearbyVariant(Entity entity, AttachmentType<Integer> attachment) {
        if (!(entity.level() instanceof Level level)) {
            return null;
        }

        AABB box = entity.getBoundingBox().inflate(CLUSTER_RADIUS);
        List<Entity> nearby = level.getEntities(
            entity,
            box,
            other -> other.getType() == entity.getType() && other.hasAttached(attachment)
        );
        if (nearby.isEmpty()) {
            return null;
        }

        Entity closest = nearby.getFirst();
        double best = entity.distanceToSqr(closest);
        for (int i = 1; i < nearby.size(); i++) {
            Entity other = nearby.get(i);
            double distance = entity.distanceToSqr(other);
            if (distance < best) {
                best = distance;
                closest = other;
            }
        }
        return closest.getAttachedOrElse(attachment, 0);
    }
}
