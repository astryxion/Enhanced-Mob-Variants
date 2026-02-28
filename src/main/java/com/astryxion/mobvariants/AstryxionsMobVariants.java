package com.astryxion.mobvariants;

import com.astryxion.mobvariants.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
    modid = AstryxionsMobVariants.MODID,
    name = AstryxionsMobVariants.NAME,
    version = AstryxionsMobVariants.VERSION
)
public class AstryxionsMobVariants {

    public static final String MODID = "astryxions_mob_variants";
    public static final String NAME = "Astryxion''s Mob Variants";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {}

    @SideOnly(Side.CLIENT)
    @Mod.EventBusSubscriber(Side.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {

            RenderingRegistry.registerEntityRenderingHandler(EntityCow.class, VariantCowRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityOcelot.class, VariantOcelotRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityChicken.class, VariantChickenRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, VariantPigRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntitySkeleton.class, VariantSkeletonRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntitySpider.class, VariantSpiderRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, VariantZombieRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, VariantWolfRenderer::new);
        }
    }
}
