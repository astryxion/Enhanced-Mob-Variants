package com.astryxion.moremobvariants;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.animal.sheep.SheepFurModel;
import net.minecraft.client.model.animal.sheep.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.client.renderer.entity.state.CowRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PigRenderState;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = MoreMobVariants.MODID, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ModList.get()
            .getModContainerById(MoreMobVariants.MODID)
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
            case 7 -> "skeleton";
            default -> "basenji";
        };
    }

    // --- RENDER STATES (carry attachment variant for texture / wool paths) ---

    private static final class MmvChickenRenderState extends ChickenRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvCowRenderState extends CowRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvCatRenderState extends CatRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvPigRenderState extends PigRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvSkeletonRenderState extends SkeletonRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvSpiderRenderState extends LivingEntityRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvZombieRenderState extends ZombieRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvSheepRenderState extends SheepRenderState {
        int moreMobVariantsVariant;
    }

    private static final class MmvWolfRenderState extends WolfRenderState {
        int moreMobVariantsVariant;
    }

    // --- RENDERERS ---

    private static class VariantChickenRenderer extends ChickenRenderer {
        public VariantChickenRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ChickenRenderState createRenderState() {
            return new MmvChickenRenderState();
        }

        @Override
        public void extractRenderState(Chicken entity, ChickenRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvChickenRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.CHICKEN_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(ChickenRenderState state) {
            if (state instanceof MmvChickenRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/chicken/chicken_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantCowRenderer extends CowRenderer {
        public VariantCowRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public CowRenderState createRenderState() {
            return new MmvCowRenderState();
        }

        @Override
        public void extractRenderState(Cow entity, CowRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvCowRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.COW_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(CowRenderState state) {
            if (state instanceof MmvCowRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/cow/cow_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantCatRenderer extends CatRenderer {
        public VariantCatRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public CatRenderState createRenderState() {
            return new MmvCatRenderState();
        }

        @Override
        public void extractRenderState(Cat entity, CatRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvCatRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.CAT_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(CatRenderState state) {
            if (state instanceof MmvCatRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(
                        MoreMobVariants.MODID,
                        "textures/entity/cat/" + getCatTextureBaseName(v) + ".png"
                    );
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantPigRenderer extends PigRenderer {
        public VariantPigRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public PigRenderState createRenderState() {
            return new MmvPigRenderState();
        }

        @Override
        public void extractRenderState(Pig entity, PigRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvPigRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.PIG_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(PigRenderState state) {
            if (state instanceof MmvPigRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/pig/pig_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantSkeletonRenderer extends SkeletonRenderer {
        public VariantSkeletonRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public SkeletonRenderState createRenderState() {
            return new MmvSkeletonRenderState();
        }

        @Override
        public void extractRenderState(Skeleton entity, SkeletonRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvSkeletonRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.SKELETON_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(SkeletonRenderState state) {
            if (state instanceof MmvSkeletonRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/skeleton/skeleton_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantSpiderRenderer extends SpiderRenderer<Spider> {
        public VariantSpiderRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public LivingEntityRenderState createRenderState() {
            return new MmvSpiderRenderState();
        }

        @Override
        public void extractRenderState(Spider entity, LivingEntityRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvSpiderRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.SPIDER_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(LivingEntityRenderState state) {
            if (state instanceof MmvSpiderRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/spider/spider_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantZombieRenderer extends ZombieRenderer {
        public VariantZombieRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public ZombieRenderState createRenderState() {
            return new MmvZombieRenderState();
        }

        @Override
        public void extractRenderState(Zombie entity, ZombieRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvZombieRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.ZOMBIE_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(ZombieRenderState state) {
            if (state instanceof MmvZombieRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/zombie/zombie_" + v + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    private static class VariantWolfRenderer extends WolfRenderer {
        public VariantWolfRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
        }

        @Override
        public WolfRenderState createRenderState() {
            return new MmvWolfRenderState();
        }

        @Override
        public void extractRenderState(Wolf entity, WolfRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvWolfRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.WOLF_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(WolfRenderState state) {
            if (state instanceof MmvWolfRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    String base = getWolfBreedName(v);
                    String suffix = state.collarColor != null ? "tame" : (state.isAngry ? "angry" : "wild");
                    return Identifier.fromNamespaceAndPath(
                        MoreMobVariants.MODID,
                        "textures/entity/wolf/" + base + "_" + suffix + ".png"
                    );
                }
            }
            return super.getTextureLocation(state);
        }
    }

    // --- SHEEP LOGIC ---

    private static class VariantSheepRenderer extends SheepRenderer {
        public VariantSheepRenderer(EntityRendererProvider.Context ctx) {
            super(ctx);
            this.layers.removeIf(layer -> layer.getClass() == SheepWoolLayer.class);
            this.addLayer(new VariantSheepWoolLayer(this, ctx.getModelSet()));
        }

        @Override
        public SheepRenderState createRenderState() {
            return new MmvSheepRenderState();
        }

        @Override
        public void extractRenderState(Sheep entity, SheepRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            if (state instanceof MmvSheepRenderState m) {
                m.moreMobVariantsVariant = entity.getData(Registration.SHEEP_VARIANT);
            }
        }

        @Override
        public Identifier getTextureLocation(SheepRenderState state) {
            if (state instanceof MmvSheepRenderState m) {
                int v = m.moreMobVariantsVariant;
                if (v != 0) {
                    return Identifier.fromNamespaceAndPath(MoreMobVariants.MODID, "textures/entity/sheep/" + getSheepName(v) + ".png");
                }
            }
            return super.getTextureLocation(state);
        }
    }

    /**
     * Same behavior as {@link SheepWoolLayer}: vanilla wool when {@code moreMobVariantsVariant == 0},
     * mod wool texture and full white tint when {@code moreMobVariantsVariant > 0} (matches prior {@code 0xFFFFFFFF}).
     */
    private static class VariantSheepWoolLayer extends RenderLayer<SheepRenderState, SheepModel> {
        private static final Identifier SHEEP_WOOL_LOCATION = Identifier.withDefaultNamespace("textures/entity/sheep/sheep_wool.png");
        private final EntityModel<SheepRenderState> adultModel;
        private final EntityModel<SheepRenderState> babyModel;

        public VariantSheepWoolLayer(RenderLayerParent<SheepRenderState, SheepModel> parent, EntityModelSet modelSet) {
            super(parent);
            this.adultModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_WOOL));
            this.babyModel = new SheepFurModel(modelSet.bakeLayer(ModelLayers.SHEEP_BABY_WOOL));
        }

        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, SheepRenderState state, float limbSwing, float limbSwingAmount) {
            if (!state.isSheared) {
                EntityModel<SheepRenderState> entityModel = state.isBaby ? this.babyModel : this.adultModel;
                if (state.isInvisible) {
                    if (state.appearsGlowing()) {
                        Identifier woolTexture = resolveWoolTexture(state);
                        collector.submitModel(
                            entityModel,
                            state,
                            poseStack,
                            RenderTypes.outline(woolTexture),
                            packedLight,
                            LivingEntityRenderer.getOverlayCoords(state, 0.0F),
                            -16777216,
                            null,
                            state.outlineColor,
                            null
                        );
                    }
                } else {
                    if (state instanceof MmvSheepRenderState m && m.moreMobVariantsVariant > 0) {
                        Identifier woolLoc = Identifier.fromNamespaceAndPath(
                            MoreMobVariants.MODID,
                            "textures/entity/sheep/wool/" + getSheepName(m.moreMobVariantsVariant) + ".png"
                        );
                        coloredCutoutModelCopyLayerRender(entityModel, woolLoc, poseStack, collector, packedLight, state, 0xFFFFFFFF, 0);
                    } else {
                        coloredCutoutModelCopyLayerRender(
                            entityModel,
                            SHEEP_WOOL_LOCATION,
                            poseStack,
                            collector,
                            packedLight,
                            state,
                            state.getWoolColor(),
                            0
                        );
                    }
                }
            }
        }

        private static Identifier resolveWoolTexture(SheepRenderState state) {
            if (state instanceof MmvSheepRenderState m && m.moreMobVariantsVariant > 0) {
                return Identifier.fromNamespaceAndPath(
                    MoreMobVariants.MODID,
                    "textures/entity/sheep/wool/" + getSheepName(m.moreMobVariantsVariant) + ".png"
                );
            }
            return SHEEP_WOOL_LOCATION;
        }
    }
}
