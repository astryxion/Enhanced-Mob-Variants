package com.astryxion.emv;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientHandler {
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityChicken.class, new VariantChickenRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityCow.class, new VariantCowRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelot.class, new VariantCatRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, new VariantPigRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySkeleton.class, new VariantSkeletonRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySpider.class, new VariantSpiderRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, new VariantZombieRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntitySheep.class, new VariantSheepRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityWolf.class, new VariantWolfRenderer());
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
        public VariantChickenRenderer() {
            super(new ModelChicken(), 0.3F);
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
        public VariantCowRenderer() {
            super(new ModelCow(), 0.7F);
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
        public VariantCatRenderer() {
            super(new ModelOcelot(), 0.4F);
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
        public VariantPigRenderer() {
            super(new ModelPig(), new ModelPig(0.5F), 0.7F);
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
        @Override
        protected ResourceLocation getEntityTexture(EntitySkeleton entity) {
            int v = Registration.get(entity);
            if (entity.getSkeletonType() == 0 && v != 0 && Config.skeleton) {
                return emvTex(variantPath("skeleton", getSkeletonName(v)));
            }
            return super.getEntityTexture(entity);
        }
    }

    private static class VariantSpiderRenderer extends RenderSpider {
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
        public VariantWolfRenderer() {
            super(new ModelWolf(), new ModelWolf(), 0.5F);
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
        public VariantSheepRenderer() {
            super(new ModelSheep2(), new ModelSheep1(), 0.7F);
        }

        @Override
        protected ResourceLocation getEntityTexture(EntitySheep entity) {
            int v = Registration.get(entity);
            if (v != 0 && Config.sheep) {
                return emvTex(variantPath("sheep", getSheepName(v)));
            }
            return super.getEntityTexture(entity);
        }

        @Override
        protected int shouldRenderPass(EntitySheep sheep, int pass, float partial) {
            if (pass == 0 && !sheep.getSheared()) {
                int variant = Registration.get(sheep);
                if (variant > 0 && Config.sheep) {
                    this.bindTexture(emvTex("textures/entity/sheep/wool/" + getSheepName(variant) + ".png"));
                    GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    return 1;
                }
            }
            return super.shouldRenderPass(sheep, pass, partial);
        }
    }
}
