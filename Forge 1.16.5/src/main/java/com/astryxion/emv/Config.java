package com.astryxion.emv;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Locale;

public class Config {
    public enum SpawnMode {
        RANDOM,
        UNIFORM
    }

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue CHICKEN = toggle("chicken", "Chicken");
    public static final ForgeConfigSpec.BooleanValue COW = toggle("cow", "Cow");
    public static final ForgeConfigSpec.BooleanValue CAT = toggle("cat", "Cat");
    public static final ForgeConfigSpec.BooleanValue PIG = toggle("pig", "Pig");
    public static final ForgeConfigSpec.BooleanValue SHEEP = toggle("sheep", "Sheep");
    public static final ForgeConfigSpec.BooleanValue WOLF = toggle("wolf", "Wolf");
    public static final ForgeConfigSpec.BooleanValue ZOMBIE = toggle("zombie", "Zombie");
    public static final ForgeConfigSpec.BooleanValue SKELETON = toggle("skeleton", "Skeleton");
    public static final ForgeConfigSpec.BooleanValue SPIDER = toggle("spider", "Spider");

    static final ForgeConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.EnumValue<SpawnMode> SPAWN_MODE = COMMON_BUILDER
        .comment(
            "Random: each mob can roll any texture. A spawn cluster can mix variants, and breeding can produce any texture.",
            "Uniform: mobs that spawn in the same group share one texture, and babies inherit that same texture."
        )
        .translation("emv.configuration.spawn_mode")
        .defineEnum("spawn_mode", SpawnMode.RANDOM);

    static final ForgeConfigSpec COMMON_SPEC = COMMON_BUILDER.build();

    private static ForgeConfigSpec.BooleanValue toggle(String key, String mob) {
        return CLIENT_BUILDER
            .comment("Show custom " + mob.toLowerCase(Locale.ROOT) + " variants.")
            .translation("emv.configuration." + key)
            .define(key, true);
    }

    public static boolean enabled(ForgeConfigSpec.BooleanValue value) {
        return !CLIENT_SPEC.isLoaded() || value.get();
    }

    public static SpawnMode spawnMode() {
        return !COMMON_SPEC.isLoaded() ? SpawnMode.RANDOM : SPAWN_MODE.get();
    }

    public static boolean isUniform() {
        return spawnMode() == SpawnMode.UNIFORM;
    }

    public static void save() {
        if (CLIENT_SPEC.isLoaded()) {
            CLIENT_SPEC.save();
        }
        if (COMMON_SPEC.isLoaded()) {
            COMMON_SPEC.save();
        }
    }
}
