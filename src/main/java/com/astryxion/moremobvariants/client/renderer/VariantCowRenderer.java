package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class VariantCowRenderer extends RenderCow {

    /** Matches {@code assets/moremobvariants/textures/entity/cow/cow_*.png} */
    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantCowRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCow cow) {
        int variant = cow.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(cow);
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/cow/cow_" + variant + ".png");
    }
}
