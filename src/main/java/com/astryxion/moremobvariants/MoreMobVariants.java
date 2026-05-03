package com.astryxion.moremobvariants;

import com.astryxion.moremobvariants.client.renderer.*;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreMobVariants.MODID)
public class MoreMobVariants {

    public static final String MODID = "moremobvariants";

    public MoreMobVariants() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityType.COW, VariantCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CAT, VariantCatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CHICKEN, VariantChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.PIG, VariantPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SKELETON, VariantSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SPIDER, VariantSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.ZOMBIE, VariantZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, VariantWolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SHEEP, VariantSheepRenderer::new);
    }
}
