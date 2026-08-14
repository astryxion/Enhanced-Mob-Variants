package com.astryxion.emv;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EnhancedMobVariants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModLoadingContext.get().registerExtensionPoint(
            ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((mc, parent) -> new EmvConfigScreen(parent))
        ));
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.CHICKEN, VariantChickenRenderer::new);
        event.registerEntityRenderer(EntityType.COW, VariantCowRenderer::new);
        event.registerEntityRenderer(EntityType.CAT, VariantCatRenderer::new);
        event.registerEntityRenderer(EntityType.PIG, VariantPigRenderer::new);
        event.registerEntityRenderer(EntityType.SKELETON, VariantSkeletonRenderer::new);
        event.registerEntityRenderer(EntityType.SPIDER, VariantSpiderRenderer::new);
        event.registerEntityRenderer(EntityType.ZOMBIE, VariantZombieRenderer::new);
        event.registerEntityRenderer(EntityType.SHEEP, VariantSheepRenderer::new);
        event.registerEntityRenderer(EntityType.WOLF, VariantWolfRenderer::new);
    }

    private static ResourceLocation emvTex(String path) {
        return new ResourceLocation(EnhancedMobVariants.MODID, path);
    }

    private static String variantPath(String mob, String name) {
        return "textures/entity/" + mob + "/" + name + ".png";
    }

    private static String getChickenName(int variant) {
        return switch (variant) {
            case 1 -> "amber";
            case 2 -> "bronzed";
            case 3 -> "duck";
            case 4 -> "gold_crested";
            case 5 -> "midnight";
            case 6 -> "skewbald";
            case 7 -> "stormy";
            default -> "amber";
        };
    }

    private static String getCowName(int variant) {
        return switch (variant) {
            case 1 -> "ashen";
            case 2 -> "umbra";
            case 3 -> "cookie";
            case 4 -> "wooly";
            case 5 -> "sunset";
            case 6 -> "pinto";
            case 7 -> "dairy";
            case 8 -> "cream";
            case 9 -> "albino";
            default -> "ashen";
        };
    }

    private static String getPigName(int variant) {
        return switch (variant) {
            case 1 -> "pink_footed";
            case 2 -> "mottled";
            case 3 -> "sooty";
            case 4 -> "spotted";
            case 5 -> "piebald";
            default -> "pink_footed";
        };
    }

    private static String getSkeletonName(int variant) {
        return switch (variant) {
            case 1 -> "dungeons";
            case 2 -> "mossy";
            case 3 -> "sandy";
            case 4 -> "weathered";
            default -> "dungeons";
        };
    }

    private static String getSpiderName(int variant) {
        return switch (variant) {
            case 1 -> "black_widow";
            case 2 -> "bone";
            case 3 -> "brown";
            case 4 -> "tarantula";
            default -> "black_widow";
        };
    }

    private static String getZombieName(int variant) {
        return switch (variant) {
            case 1 -> "alex";
            case 2 -> "ari";
            case 3 -> "efe";
            case 4 -> "kai";
            case 5 -> "makena";
            case 6 -> "noor";
            case 7 -> "sunny";
            case 8 -> "zuri";
            default -> "alex";
        };
    }

    private static String getCatTextureBaseName(int variant) {
        return switch (variant) {
            case 1 -> "anita_hart";
            case 2 -> "doug";
            case 3 -> "gray_tabby";
            case 4 -> "handsome";
            case 5 -> "tortoiseshell";
            default -> "gray_tabby";
        };
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

    private static String getWolfBreedName(int variant) {
        return switch (variant) {
            case 1 -> "basenji";
            case 2 -> "french_bulldog";
            case 3 -> "german_shepherd";
            case 4 -> "golden_retriever";
            case 5 -> "husky";
            case 6 -> "jupiter";
            default -> "basenji";
        };
    }

    private static class VariantChickenRenderer extends ChickenRenderer {
        public VariantChickenRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Chicken entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.CHICKEN)) {
                return emvTex(variantPath("chicken", getChickenName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantCowRenderer extends CowRenderer {
        public VariantCowRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Cow entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.COW)) {
                return emvTex(variantPath("cow", getCowName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantCatRenderer extends CatRenderer {
        public VariantCatRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Cat entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.CAT)) {
                return emvTex(variantPath("cat", getCatTextureBaseName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantPigRenderer extends PigRenderer {
        public VariantPigRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Pig entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.PIG)) {
                return emvTex(variantPath("pig", getPigName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSkeletonRenderer extends SkeletonRenderer {
        public VariantSkeletonRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
            int v = Registration.get(entity);
            if (entity instanceof Skeleton && v != 0 && Config.enabled(Config.SKELETON)) {
                return emvTex(variantPath("skeleton", getSkeletonName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSpiderRenderer extends SpiderRenderer<Spider> {
        public VariantSpiderRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Spider entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.SPIDER)) {
                return emvTex(variantPath("spider", getSpiderName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantZombieRenderer extends ZombieRenderer {
        public VariantZombieRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Zombie entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.ZOMBIE)) {
                return emvTex(variantPath("zombie", getZombieName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantWolfRenderer extends WolfRenderer {
        public VariantWolfRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Wolf entity) {
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
        public VariantSheepRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
            this.layers.removeIf(layer -> layer.getClass() == SheepFurLayer.class);
            this.addLayer(new VariantSheepWoolLayer(this, ctx.getModelSet()));
        }

        @Override
        public ResourceLocation getTextureLocation(Sheep entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.enabled(Config.SHEEP)) {
                return emvTex(variantPath("sheep", getSheepName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    /**
     * Same behavior as {@link SheepFurLayer}: vanilla wool when the variant is 0,
     * mod wool texture and full white tint when the variant is greater than 0.
     */
    private static class VariantSheepWoolLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
        private static final ResourceLocation SHEEP_FUR_LOCATION =
            new ResourceLocation("minecraft", "textures/entity/sheep/sheep_fur.png");
        private final SheepFurModel<Sheep> model;

        public VariantSheepWoolLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> parent, EntityModelSet modelSet) {
            super(parent);
            this.model = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
        }

        @Override
        public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            Sheep sheep,
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
                    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.outline(woolTexture));
                    this.model.renderToBuffer(
                        poseStack,
                        vertexConsumer,
                        packedLight,
                        LivingEntityRenderer.getOverlayCoords(sheep, 0.0F),
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

            coloredCutoutModelCopyLayerRender(
                this.getParentModel(),
                this.model,
                woolTexture,
                poseStack,
                buffer,
                packedLight,
                sheep,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch,
                partialTick,
                red,
                green,
                blue
            );
        }

        private static ResourceLocation resolveWoolTexture(Sheep sheep) {
            int variant = Registration.get(sheep);
            if (variant > 0 && Config.enabled(Config.SHEEP)) {
                return emvTex("textures/entity/sheep/wool/" + getSheepName(variant) + ".png");
            }
            return SHEEP_FUR_LOCATION;
        }
    }
}
