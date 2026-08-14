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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = EnhancedMobVariants.MODID, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModList.get()
            .getModContainerById(EnhancedMobVariants.MODID)
            .ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new)));
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

    /** Base filename (no .png) under {@code textures/entity/cat/}; 0 = vanilla. */
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

    /** Asset base name under {@code textures/entity/wolf/<name>_{wild,tame,angry}.png}; 0 = vanilla (not used here). */
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
            int v = entity.getData(Registration.CHICKEN_VARIANT);
            if (v != 0 && Config.enabled(Config.CHICKEN)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("chicken", getChickenName(v)));
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
            int v = entity.getData(Registration.COW_VARIANT);
            if (v != 0 && Config.enabled(Config.COW)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("cow", getCowName(v)));
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
            int v = entity.getData(Registration.CAT_VARIANT);
            if (v != 0 && Config.enabled(Config.CAT)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("cat", getCatTextureBaseName(v)));
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
            int v = entity.getData(Registration.PIG_VARIANT);
            if (v != 0 && Config.enabled(Config.PIG)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("pig", getPigName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    private static class VariantSkeletonRenderer extends SkeletonRenderer<Skeleton> {
        public VariantSkeletonRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ResourceLocation getTextureLocation(Skeleton entity) {
            int v = entity.getData(Registration.SKELETON_VARIANT);
            if (v != 0 && Config.enabled(Config.SKELETON)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("skeleton", getSkeletonName(v)));
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
            int v = entity.getData(Registration.SPIDER_VARIANT);
            if (v != 0 && Config.enabled(Config.SPIDER)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("spider", getSpiderName(v)));
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
            int v = entity.getData(Registration.ZOMBIE_VARIANT);
            if (v != 0 && Config.enabled(Config.ZOMBIE)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("zombie", getZombieName(v)));
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
            int v = entity.getData(Registration.WOLF_VARIANT);
            if (v >= 1 && v <= 6 && Config.enabled(Config.WOLF)) {
                String base = getWolfBreedName(v);
                String suffix = entity.isTame() ? "tame" : (entity.isAngry() ? "angry" : "wild");
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("wolf", base + "_" + suffix));
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
            int v = entity.getData(Registration.SHEEP_VARIANT);
            if (v != 0 && Config.enabled(Config.SHEEP)) {
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, variantPath("sheep", getSheepName(v)));
            }
            return super.getTextureLocation(entity);
        }
    }

    /**
     * Same behavior as {@link SheepFurLayer}: vanilla wool when the variant is 0,
     * mod wool texture and full white tint when the variant is greater than 0 (matches prior {@code 0xFFFFFFFF}).
     */
    private static class VariantSheepWoolLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
        private static final ResourceLocation SHEEP_FUR_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/sheep/sheep_fur.png");
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
                    this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheep, 0.0F), -16777216);
                }
                return;
            }

            int color;
            int variant = sheep.getData(Registration.SHEEP_VARIANT);
            if (variant > 0 && Config.enabled(Config.SHEEP)) {
                color = 0xFFFFFFFF;
            } else if (sheep.hasCustomName() && "jeb_".equals(sheep.getName().getString())) {
                int period = 25;
                int cycle = sheep.tickCount / period + sheep.getId();
                int dyeCount = DyeColor.values().length;
                int from = cycle % dyeCount;
                int to = (cycle + 1) % dyeCount;
                float delta = ((float) (sheep.tickCount % period) + partialTick) / period;
                color = FastColor.ARGB32.lerp(delta, Sheep.getColor(DyeColor.byId(from)), Sheep.getColor(DyeColor.byId(to)));
            } else {
                color = Sheep.getColor(sheep.getColor());
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
                color
            );
        }

        private static ResourceLocation resolveWoolTexture(Sheep sheep) {
            int variant = sheep.getData(Registration.SHEEP_VARIANT);
            if (variant > 0 && Config.enabled(Config.SHEEP)) {
                String path = "textures/entity/sheep/wool/" + getSheepName(variant) + ".png";
                return ResourceLocation.fromNamespaceAndPath(EnhancedMobVariants.MODID, path);
            }
            return SHEEP_FUR_LOCATION;
        }
    }
}
