package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

public class VariantChickenRenderer extends RenderChicken {

    /** Matches {@code assets/moremobvariants/textures/entity/chicken/chicken_*.png} */
    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantChickenRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityChicken chicken) {
        int variant = chicken.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(chicken);
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/chicken/chicken_" + variant + ".png");
    }
}
