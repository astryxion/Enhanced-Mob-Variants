package com.astryxion.moremobvariants;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@EventBusSubscriber(modid = "moremobvariants", bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_CHICKEN_VARIANTS = BUILDER
            .comment("Whether to enable the custom chicken textures.")
            .define("enableChickenVariants", true);

    public static final ModConfigSpec.IntValue VARIANT_CHANCE = BUILDER
            .comment("The rarity of seeing a variant (1-100). Not currently used in basic logic but ready for expansion.")
            .defineInRange("variantChance", 100, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Add logic here if you need to react to config changes at runtime
    }
}