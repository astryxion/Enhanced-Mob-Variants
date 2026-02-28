package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantCow extends RenderCow {

    // 9 custom textures (cow_1 → cow_9)
    private static final ResourceLocation[] CUSTOM_TEXTURES = new ResourceLocation[9];

    static {
        for (int i = 0; i < 9; i++) {
            CUSTOM_TEXTURES[i] = new ResourceLocation(
                "moremobvariants",
                "textures/entity/cow/cow_" + (i + 1) + ".png"
            );
        }
    }

    public RenderVariantCow() {
        super(new ModelCow(), 0.7F); // correct for 1.7.10
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // Deterministic per-cow variant
        int variant = Math.abs(entity.getEntityId()) % 10;

        // 0 = vanilla cow texture
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–9 = custom textures
        return CUSTOM_TEXTURES[variant - 1];
    }
}
