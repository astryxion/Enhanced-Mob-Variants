package com.astryxion.emv;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.TranslatableEnum;

import java.util.Locale;

public class Config {
    public enum SpawnMode implements TranslatableEnum {
        RANDOM,
        UNIFORM;

        @Override
        public Component getTranslatedName() {
            return Component.translatable("emv.configuration.spawn_mode." + name().toLowerCase(Locale.ROOT));
        }
    }

    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CHICKEN = toggle("chicken", "Chicken");
    public static final ModConfigSpec.BooleanValue COW = toggle("cow", "Cow");
    public static final ModConfigSpec.BooleanValue CAT = toggle("cat", "Cat");
    public static final ModConfigSpec.BooleanValue PIG = toggle("pig", "Pig");
    public static final ModConfigSpec.BooleanValue SHEEP = toggle("sheep", "Sheep");
    public static final ModConfigSpec.BooleanValue WOLF = toggle("wolf", "Wolf");
    public static final ModConfigSpec.BooleanValue ZOMBIE = toggle("zombie", "Zombie");
    public static final ModConfigSpec.BooleanValue SKELETON = toggle("skeleton", "Skeleton");
    public static final ModConfigSpec.BooleanValue SPIDER = toggle("spider", "Spider");

    static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<SpawnMode> SPAWN_MODE = COMMON_BUILDER
        .comment(
            "Random: each mob can roll any texture. A spawn cluster can mix variants, and breeding can produce any texture.",
            "Uniform: mobs that spawn in the same group share one texture, and babies inherit that same texture."
        )
        .translation("emv.configuration.spawn_mode")
        .defineEnum("spawn_mode", SpawnMode.RANDOM);

    static final ModConfigSpec COMMON_SPEC = COMMON_BUILDER.build();

    private static ModConfigSpec.BooleanValue toggle(String key, String mob) {
        return CLIENT_BUILDER
            .comment("Show custom " + mob.toLowerCase(Locale.ROOT) + " variants.")
            .translation("emv.configuration." + key)
            .define(key, true);
    }

    public static boolean enabled(ModConfigSpec.BooleanValue value) {
        return !CLIENT_SPEC.isLoaded() || value.get();
    }

    public static SpawnMode spawnMode() {
        return !COMMON_SPEC.isLoaded() ? SpawnMode.RANDOM : SPAWN_MODE.get();
    }

    public static boolean isUniform() {
        return spawnMode() == SpawnMode.UNIFORM;
    }
}
