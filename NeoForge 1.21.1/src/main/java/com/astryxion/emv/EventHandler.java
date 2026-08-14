package com.astryxion.emv;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

import java.util.List;
import java.util.function.Supplier;

public class EventHandler {
    private static final double CLUSTER_RADIUS = 16.0;

    public static class VariantAgeableGroupData extends AgeableMob.AgeableMobGroupData {
        public final int variant;

        public VariantAgeableGroupData(int variant) {
            super(true);
            this.variant = variant;
        }
    }

    private record Rule(Supplier<AttachmentType<Integer>> attachment, ModConfigSpec.BooleanValue toggle, int bound) {}

    private static Rule ruleFor(Entity entity) {
        if (entity instanceof Chicken) {
            return new Rule(Registration.CHICKEN_VARIANT, Config.CHICKEN, 8);
        }
        if (entity instanceof Cow) {
            return new Rule(Registration.COW_VARIANT, Config.COW, 10);
        }
        if (entity instanceof Cat) {
            return new Rule(Registration.CAT_VARIANT, Config.CAT, 6);
        }
        if (entity instanceof Pig) {
            return new Rule(Registration.PIG_VARIANT, Config.PIG, 6);
        }
        if (entity instanceof Sheep) {
            return new Rule(Registration.SHEEP_VARIANT, Config.SHEEP, 7);
        }
        if (entity instanceof Wolf) {
            return new Rule(Registration.WOLF_VARIANT, Config.WOLF, 7);
        }
        if (entity instanceof Skeleton) {
            return new Rule(Registration.SKELETON_VARIANT, Config.SKELETON, 5);
        }
        if (entity instanceof Spider spider && !(spider instanceof CaveSpider)) {
            return new Rule(Registration.SPIDER_VARIANT, Config.SPIDER, 5);
        }
        if (entity instanceof Zombie zombie && !(zombie instanceof Husk)) {
            return new Rule(Registration.ZOMBIE_VARIANT, Config.ZOMBIE, 9);
        }
        return null;
    }

    private static boolean usesSharedAgeableGroup(Entity entity) {
        return entity instanceof Chicken
            || entity instanceof Cow
            || entity instanceof Cat
            || entity instanceof Pig
            || entity instanceof Sheep;
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        Rule rule = ruleFor(mob);
        if (rule == null || mob.level().isClientSide()) {
            return;
        }

        if (!Config.enabled(rule.toggle())) {
            setIfAbsent(mob, rule, 0);
            return;
        }

        if (!Config.isUniform() || !usesSharedAgeableGroup(mob)) {
            return;
        }

        SpawnGroupData spawnData = event.getSpawnData();
        if (spawnData instanceof VariantAgeableGroupData pack) {
            setIfAbsent(mob, rule, pack.variant);
            return;
        }

        if (spawnData == null) {
            int variant = mob.getRandom().nextInt(rule.bound());
            event.setSpawnData(new VariantAgeableGroupData(variant));
            setIfAbsent(mob, rule, variant);
        }
    }

    @SubscribeEvent
    public static void onBabySpawn(BabyEntitySpawnEvent event) {
        AgeableMob child = event.getChild();
        if (child == null) {
            return;
        }

        Rule rule = ruleFor(child);
        if (rule == null || child.level().isClientSide() || child.hasData(rule.attachment())) {
            return;
        }

        if (!Config.enabled(rule.toggle())) {
            child.setData(rule.attachment(), 0);
            return;
        }

        child.setData(rule.attachment(), breedVariant(event.getParentA(), event.getParentB(), rule));
    }

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Entity entity = event.getEntity();
        Rule rule = ruleFor(entity);
        if (rule == null || entity.hasData(rule.attachment())) {
            return;
        }

        if (!Config.enabled(rule.toggle())) {
            entity.setData(rule.attachment(), 0);
            return;
        }

        entity.setData(rule.attachment(), pickSpawnVariant(entity, rule));
    }

    private static void setIfAbsent(Entity entity, Rule rule, int variant) {
        if (!entity.hasData(rule.attachment())) {
            entity.setData(rule.attachment(), variant);
        }
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

    private static int breedVariant(Mob parentA, Mob parentB, Rule rule) {
        if (Config.isUniform()) {
            boolean hasA = parentA.hasData(rule.attachment());
            boolean hasB = parentB.hasData(rule.attachment());
            if (hasA && hasB) {
                int a = parentA.getData(rule.attachment());
                int b = parentB.getData(rule.attachment());
                return a == b || parentA.getRandom().nextBoolean() ? a : b;
            }
            if (hasA) {
                return parentA.getData(rule.attachment());
            }
            if (hasB) {
                return parentB.getData(rule.attachment());
            }
        }
        return parentA.getRandom().nextInt(rule.bound());
    }

    private static Integer findNearbyVariant(Entity entity, Supplier<AttachmentType<Integer>> attachment) {
        if (!(entity.level() instanceof Level level)) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Class<Entity> type = (Class<Entity>) entity.getClass();
        AABB box = entity.getBoundingBox().inflate(CLUSTER_RADIUS);
        List<Entity> nearby = level.getEntitiesOfClass(type, box, other -> other != entity && other.hasData(attachment));
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
        return closest.getData(attachment);
    }
}
