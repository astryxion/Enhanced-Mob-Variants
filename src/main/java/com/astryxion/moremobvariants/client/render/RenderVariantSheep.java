package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Sheep use two layers in 1.7.10: base body ({@code textures/entity/sheep/sheep.png}) and an extra pass
 * with {@code sheep_fur.png} plus dye tint. Custom breeds mirror that layout: root PNG + matching {@code wool/} PNG.
 */
public class RenderVariantSheep extends RenderSheep {

    private static final String[] VARIANT_NAMES = new String[] {
        "fuzzy",
        "inky",
        "long_nosed",
        "patched",
        "rainbow",
        "rocky"
    };

    private static final int CUSTOM_COUNT = VARIANT_NAMES.length;

    public RenderVariantSheep() {
        super(new ModelSheep2(), new ModelSheep1(), 0.7F);
    }

    private static int variantIndex(EntitySheep sheep) {
        return Math.abs(sheep.getEntityId()) % (CUSTOM_COUNT + 1);
    }

    private static ResourceLocation bodyTexture(int variant) {
        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/sheep/" + VARIANT_NAMES[variant - 1] + ".png"
        );
    }

    private static ResourceLocation woolTexture(int variant) {
        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/sheep/wool/" + VARIANT_NAMES[variant - 1] + ".png"
        );
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        EntitySheep sheep = (EntitySheep) entity;
        int v = variantIndex(sheep);
        if (v == 0) {
            return super.getEntityTexture(entity);
        }
        return bodyTexture(v);
    }

    @Override
    protected int shouldRenderPass(EntitySheep sheep, int pass, float partialTick) {
        int v = variantIndex(sheep);
        if (v == 0) {
            return super.shouldRenderPass(sheep, pass, partialTick);
        }
        if (pass == 0 && !sheep.getSheared()) {
            this.bindTexture(woolTexture(v));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float[] tint = EntitySheep.fleeceColorTable[sheep.getFleeceColor()];
            GL11.glColor3f(tint[0], tint[1], tint[2]);
            return 1;
        }
        return -1;
    }

    @Override
    protected int inheritRenderPass(EntityLivingBase entity, int pass, float partialTick) {
        EntitySheep sheep = (EntitySheep) entity;
        int v = variantIndex(sheep);
        if (v != 0 && pass == 0) {
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        return super.inheritRenderPass(entity, pass, partialTick);
    }
}
