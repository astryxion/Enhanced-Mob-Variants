package com.astryxion.moremobvariants;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = MoreMobVariants.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    public ClientHandler(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.CHICKEN, VariantChickenRenderer::new);
        event.registerEntityRenderer(EntityType.COW, VariantCowRenderer::new);
        event.registerEntityRenderer(EntityType.PIG, VariantPigRenderer::new);
        event.registerEntityRenderer(EntityType.SKELETON, VariantSkeletonRenderer::new);
        event.registerEntityRenderer(EntityType.SPIDER, VariantSpiderRenderer::new);
        event.registerEntityRenderer(EntityType.ZOMBIE, VariantZombieRenderer::new);
        event.registerEntityRenderer(EntityType.SHEEP, VariantSheepRenderer::new);
    }

    private static String getSheepName(int variant) {
        return switch (variant) {
            case 1 -> "fuzzy";
            case 2 -> "inky";
            case 3 -> "long_nosed";
            case 4 -> "patched";
            case 5 -> "rainbow";
            case 6 -> "rocky";
            default -> "vanilla";
        };
    }

    // --- RENDERERS ---

    private static class VariantChickenRenderer extends ChickenRenderer {
        public VariantChickenRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(Chicken e) {
            int v = e.getData(Registration.CHICKEN_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/chicken/chicken_" + v + ".png");
        }
    }

    private static class VariantCowRenderer extends CowRenderer {
        public VariantCowRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(Cow e) {
            int v = e.getData(Registration.COW_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/cow/cow_" + v + ".png");
        }
    }

    private static class VariantPigRenderer extends PigRenderer {
        public VariantPigRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(Pig e) {
            int v = e.getData(Registration.PIG_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/pig/pig_" + v + ".png");
        }
    }

    private static class VariantSkeletonRenderer extends SkeletonRenderer {
        public VariantSkeletonRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(AbstractSkeleton e) {
            int v = e.getData(Registration.SKELETON_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/skeleton/skeleton_" + v + ".png");
        }
    }

    private static class VariantSpiderRenderer extends SpiderRenderer {
        public VariantSpiderRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(Spider e) {
            int v = e.getData(Registration.SPIDER_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/spider/spider_" + v + ".png");
        }
    }

    private static class VariantZombieRenderer extends ZombieRenderer {
        public VariantZombieRenderer(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(Zombie e) {
            int v = e.getData(Registration.ZOMBIE_VARIANT);
            return v == 0 ? super.getTextureLocation(e) : ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/zombie/zombie_" + v + ".png");
        }
    }

    // --- SHEEP LOGIC ---

    private static class VariantSheepRenderer extends SheepRenderer {
        public VariantSheepRenderer(EntityRendererProvider.Context ctx) { 
            super(ctx); 
            this.addLayer(new VariantSheepFurLayer(this, ctx.getModelSet()));
        }

        @Override
        public ResourceLocation getTextureLocation(Sheep e) {
            int v = e.getData(Registration.SHEEP_VARIANT);
            if (v == 0) return super.getTextureLocation(e);
            
            // This now pulls the base skin (face/legs) directly from textures/entity/sheep/
            return ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/sheep/" + getSheepName(v) + ".png");
        }
    }

    private static class VariantSheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
        private final SheepFurModel<Sheep> model;

        public VariantSheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> parent, EntityModelSet modelSet) {
            super(parent);
            this.model = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, Sheep sheep, float swing, float amount, float partial, float age, float yaw, float pitch) {
            int v = sheep.getData(Registration.SHEEP_VARIANT);
            if (v > 0 && !sheep.isSheared() && !sheep.isInvisible()) {
                ResourceLocation woolLoc = ResourceLocation.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/sheep/wool/" + getSheepName(v) + ".png");
                
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(sheep, swing, amount, partial);
                this.model.setupAnim(sheep, swing, amount, age, yaw, pitch);
                
                VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(woolLoc));
                
                // 0xFFFFFFFF ensures the wool displays your custom colors (like Rainbow) without dye interference
                this.model.renderToBuffer(pose, vc, light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            }
        }
    }
}