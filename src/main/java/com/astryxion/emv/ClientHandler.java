package com.astryxion.emv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EnhancedMobVariants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
            () -> (mc, parent) -> new EmvConfigScreen(parent));
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CHICKEN, VariantChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.COW, VariantCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.CAT, VariantCatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.PIG, VariantPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SKELETON, VariantSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SPIDER, VariantSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.ZOMBIE, VariantZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.SHEEP, VariantSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityType.WOLF, VariantWolfRenderer::new);
    }

    private static ResourceLocation emvTex(String path) {
        return new ResourceLocation(EnhancedMobVariants.MODID, path);
    }

    private static String variantPath(String mob, String name) {
        return "textures/entity/" + mob + "/" + name + ".png";
    }

    private static String getChickenName(int variant) {
        switch (variant) {
            case 1: return "amber";
            case 2: return "bronzed";
            case 3: return "duck";
            case 4: return "gold_crested";
            case 5: return "midnight";
            case 6: return "skewbald";
            case 7: return "stormy";
            default: return "amber";
        }
    }

    private static String getCowName(int variant) {
        switch (variant) {
            case 1: return "ashen";
            case 2: return "umbra";
            case 3: return "cookie";
            case 4: return "wooly";
            case 5: return "sunset";
            case 6: return "pinto";
            case 7: return "dairy";
            case 8: return "cream";
            case 9: return "albino";
            default: return "ashen";
        }
    }

    private static String getPigName(int variant) {
        switch (variant) {
            case 1: return "pink_footed";
            case 2: return "mottled";
            case 3: return "sooty";
            case 4: return "spotted";
            case 5: return "piebald";
            default: return "pink_footed";
        }
    }

    private static String getSkeletonName(int variant) {
        switch (variant) {
            case 1: return "dungeons";
            case 2: return "mossy";
            case 3: return "sandy";
            case 4: return "weathered";
            default: return "dungeons";
        }
    }

    private static String getSpiderName(int variant) {
        switch (variant) {
            case 1: return "black_widow";
            case 2: return "bone";
            case 3: return "brown";
            case 4: return "tarantula";
            default: return "black_widow";
        }
    }

    private static String getZombieName(int variant) {
        switch (variant) {
            case 1: return "alex";
            case 2: return "ari";
            case 3: return "efe";
            case 4: return "kai";
            case 5: return "makena";
            case 6: return "noor";
            case 7: return "sunny";
            case 8: return "zuri";
            default: return "alex";
        }
    }

    private static String getCatTextureBaseName(int variant) {
        switch (variant) {
            case 1: return "anita_hart";
            case 2: return "doug";
            case 3: return "gray_tabby";
            case 4: return "handsome";
            case 5: return "tortoiseshell";
            default: return "gray_tabby";
        }
    }

    private static String getSheepName(int variant) {
        switch (variant) {
            case 1: return "fuzzy";
            case 2: return "inky";
            case 3: return "long_nosed";
            case 4: return "patched";
            case 5: return "rainbow";
            case 6: return "rocky";
            default: return "vanilla";
        }
    }

    private static String getWolfBreedName(int variant) {
        switch (variant) {
            case 1: return "basenji";
            case 2: return "french_bulldog";
            case 3: return "german_shepherd";
            case 4: return "golden_retriever";
            case 5: return "husky";
            case 6: return "jupiter";
            default: return "basenji";
        }
    }

    private static class VariantChickenRenderer extends ChickenRenderer {
        public VariantChickenRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(ChickenEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.CHICKEN)) {
                return emvTex(variantPath("chicken", getChickenName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantCowRenderer extends CowRenderer {
        public VariantCowRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(CowEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.COW)) {
                return emvTex(variantPath("cow", getCowName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantCatRenderer extends CatRenderer {
        public VariantCatRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(CatEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.CAT)) {
                return emvTex(variantPath("cat", getCatTextureBaseName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantPigRenderer extends PigRenderer {
        public VariantPigRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(PigEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.PIG)) {
                return emvTex(variantPath("pig", getPigName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSkeletonRenderer extends SkeletonRenderer {
        public VariantSkeletonRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractSkeletonEntity entity) {
            int v = Registration.get(entity);
            if (entity instanceof SkeletonEntity && v != 0 && Config.enabled(Config.SKELETON)) {
                return emvTex(variantPath("skeleton", getSkeletonName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSpiderRenderer extends SpiderRenderer<SpiderEntity> {
        public VariantSpiderRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(SpiderEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.SPIDER)) {
                return emvTex(variantPath("spider", getSpiderName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantZombieRenderer extends ZombieRenderer {
        public VariantZombieRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(ZombieEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.ZOMBIE)) {
                return emvTex(variantPath("zombie", getZombieName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantWolfRenderer extends WolfRenderer {
        public VariantWolfRenderer(EntityRendererManager manager) {
            super(manager);
        }

        @Override
        public ResourceLocation getTextureLocation(WolfEntity entity) {
            int v = Registration.get(entity);
            if (v >= 1 && v <= 6 && Config.enabled(Config.WOLF)) {
                String base = getWolfBreedName(v);
                String suffix = entity.isTame() ? "tame" : (entity.isAngry() ? "angry" : "wild");
                return emvTex(variantPath("wolf", base + "_" + suffix));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSheepRenderer extends SheepRenderer {
        public VariantSheepRenderer(EntityRendererManager manager) {
            super(manager);
            this.layers.removeIf(layer -> layer.getClass() == SheepWoolLayer.class);
            this.addLayer(new VariantSheepWoolLayer(this));
        }

        @Override
        public ResourceLocation getTextureLocation(SheepEntity entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.SHEEP)) {
                return emvTex(variantPath("sheep", getSheepName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSheepWoolLayer extends LayerRenderer<SheepEntity, SheepModel<SheepEntity>> {
        private static final ResourceLocation SHEEP_FUR_LOCATION =
            new ResourceLocation("minecraft", "textures/entity/sheep/sheep_fur.png");
        private final SheepWoolModel<SheepEntity> model = new SheepWoolModel<>();

        public VariantSheepWoolLayer(VariantSheepRenderer parent) {
            super(parent);
        }

        @Override
        public void render(
            MatrixStack poseStack,
            IRenderTypeBuffer buffer,
            int packedLight,
            SheepEntity sheep,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
        ) {
            if (sheep.isSheared()) {
                return;
            }

            ResourceLocation woolTexture = resolveWoolTexture(sheep);
            if (sheep.isInvisible()) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.shouldEntityAppearGlowing(sheep)) {
                    this.getParentModel().copyPropertiesTo(this.model);
                    this.model.prepareMobModel(sheep, limbSwing, limbSwingAmount, partialTick);
                    this.model.setupAnim(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    IVertexBuilder vertexConsumer = buffer.getBuffer(RenderType.outline(woolTexture));
                    this.model.renderToBuffer(
                        poseStack,
                        vertexConsumer,
                        packedLight,
                        LivingRenderer.getOverlayCoords(sheep, 0.0F),
                        0.0F,
                        0.0F,
                        0.0F,
                        1.0F
                    );
                }
                return;
            }

            float red;
            float green;
            float blue;
            int variant = Registration.get(sheep);
            if (variant > 0 && Config.enabled(Config.SHEEP)) {
                red = 1.0F;
                green = 1.0F;
                blue = 1.0F;
            } else if (sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString())) {
                int period = 25;
                int cycle = sheep.tickCount / period + sheep.getId();
                int dyeCount = DyeColor.values().length;
                float delta = ((float) (sheep.tickCount % period) + partialTick) / period;
                float[] from = DyeColor.byId(cycle % dyeCount).getTextureDiffuseColors();
                float[] to = DyeColor.byId((cycle + 1) % dyeCount).getTextureDiffuseColors();
                red = from[0] * (1.0F - delta) + to[0] * delta;
                green = from[1] * (1.0F - delta) + to[1] * delta;
                blue = from[2] * (1.0F - delta) + to[2] * delta;
            } else {
                float[] color = sheep.getColor().getTextureDiffuseColors();
                red = color[0];
                green = color[1];
                blue = color[2];
            }

            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(sheep, limbSwing, limbSwingAmount, partialTick);
            this.model.setupAnim(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutoutNoCull(woolTexture));
            this.model.renderToBuffer(
                poseStack,
                builder,
                packedLight,
                LivingRenderer.getOverlayCoords(sheep, 0.0F),
                red,
                green,
                blue,
                1.0F
            );
        }

        private static ResourceLocation resolveWoolTexture(SheepEntity sheep) {
            int variant = Registration.get(sheep);
            if (variant > 0 && Config.enabled(Config.SHEEP)) {
                return emvTex("textures/entity/sheep/wool/" + getSheepName(variant) + ".png");
            }
            return SHEEP_FUR_LOCATION;
        }
    }
}
