package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class VariantPigRenderer extends RenderPig {

    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantPigRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPig pig) {
        int variant = pig.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(pig);
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/pig/pig_" + variant + ".png");
    }
}
