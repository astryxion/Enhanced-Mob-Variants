package com.astryxion.emv;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHandler {
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityChicken.class, VariantChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCow.class, VariantCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelot.class, VariantCatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, VariantPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySkeleton.class, VariantSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpider.class, VariantSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, VariantZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySheep.class, VariantSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, VariantWolfRenderer::new);
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

    private static class VariantChickenRenderer extends RenderChicken {
        public VariantChickenRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityChicken entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.chicken) {
                return emvTex(variantPath("chicken", getChickenName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantCowRenderer extends RenderCow {
        public VariantCowRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityCow entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.cow) {
                return emvTex(variantPath("cow", getCowName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantCatRenderer extends RenderOcelot {
        public VariantCatRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityOcelot entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.cat) {
                return emvTex(variantPath("cat", getCatTextureBaseName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantPigRenderer extends RenderPig {
        public VariantPigRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityPig entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.pig) {
                return emvTex(variantPath("pig", getPigName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantSkeletonRenderer extends RenderSkeleton {
        public VariantSkeletonRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(AbstractSkeleton entity) {
            int v = Registration.get(entity);
            if (entity instanceof EntitySkeleton && v != 0 && Config.skeleton) {
                return emvTex(variantPath("skeleton", getSkeletonName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantSpiderRenderer extends RenderSpider<EntitySpider> {
        public VariantSpiderRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntitySpider entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.spider) {
                return emvTex(variantPath("spider", getSpiderName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantZombieRenderer extends RenderZombie {
        public VariantZombieRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityZombie entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.zombie) {
                return emvTex(variantPath("zombie", getZombieName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantWolfRenderer extends RenderWolf {
        public VariantWolfRenderer(RenderManager manager) {
            super(manager);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntityWolf entity) {
            int v = Registration.get(entity);
            if (v >= 1 && v <= 6 && Config.wolf) {
                String base = getWolfBreedName(v);
                String suffix = entity.isTamed() ? "tame" : (entity.isAngry() ? "angry" : "wild");
                return emvTex(variantPath("wolf", base + "_" + suffix));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantSheepRenderer extends RenderSheep {
        public VariantSheepRenderer(RenderManager manager) {
            super(manager);
            this.layerRenderers.removeIf(layer -> layer instanceof LayerSheepWool);
            this.addLayer(new VariantSheepWoolLayer(this));
        }

        @Override
        protected ResourceLocation getEntityTexture(EntitySheep entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.sheep) {
                return emvTex(variantPath("sheep", getSheepName(v)));
            }
            return super.getEntityTexture(entity);
        }

        private void bindEmvTexture(ResourceLocation texture) {
            this.bindTexture(texture);
        }
    }

    private static class VariantSheepWoolLayer implements LayerRenderer<EntitySheep> {
        private static final ResourceLocation SHEEP_FUR_LOCATION =
            new ResourceLocation("textures/entity/sheep/sheep_fur.png");
        private final VariantSheepRenderer sheepRenderer;
        private final ModelSheep1 sheepModel = new ModelSheep1();

        public VariantSheepWoolLayer(VariantSheepRenderer parent) {
            this.sheepRenderer = parent;
        }

        @Override
        public void doRenderLayer(
            EntitySheep sheep,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            float scale
        ) {
            if (sheep.getSheared() || sheep.isInvisible()) {
                return;
            }

            this.sheepRenderer.bindEmvTexture(resolveWoolTexture(sheep));

            float red;
            float green;
            float blue;
            int variant = Registration.get(sheep);
            if (variant > 0 && Config.sheep) {
                red = 1.0F;
                green = 1.0F;
                blue = 1.0F;
            } else if (sheep.hasCustomName() && "jeb_".equals(sheep.getCustomNameTag())) {
                int period = 25;
                int cycle = sheep.ticksExisted / period + sheep.getEntityId();
                int dyeCount = EnumDyeColor.values().length;
                float delta = ((float) (sheep.ticksExisted % period) + partialTicks) / period;
                float[] from = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(cycle % dyeCount));
                float[] to = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata((cycle + 1) % dyeCount));
                red = from[0] * (1.0F - delta) + to[0] * delta;
                green = from[1] * (1.0F - delta) + to[1] * delta;
                blue = from[2] * (1.0F - delta) + to[2] * delta;
            } else {
                float[] color = EntitySheep.getDyeRgb(sheep.getFleeceColor());
                red = color[0];
                green = color[1];
                blue = color[2];
            }

            GlStateManager.color(red, green, blue);
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(sheep, limbSwing, limbSwingAmount, partialTicks);
            this.sheepModel.render(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }

        private static ResourceLocation resolveWoolTexture(EntitySheep sheep) {
            int variant = Registration.get(sheep);
            if (variant > 0 && Config.sheep) {
                return emvTex("textures/entity/sheep/wool/" + getSheepName(variant) + ".png");
            }
            return SHEEP_FUR_LOCATION;
        }
    }
}
