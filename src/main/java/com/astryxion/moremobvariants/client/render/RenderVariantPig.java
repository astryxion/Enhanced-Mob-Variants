package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantPig extends RenderPig {

    // 5 custom textures: pig_1 → pig_5
    private static final ResourceLocation[] CUSTOM_TEXTURES = new ResourceLocation[5];

    static {
        for (int i = 0; i < 5; i++) {
            CUSTOM_TEXTURES[i] = new ResourceLocation(
                "moremobvariants",
                "textures/entity/pig/pig_" + (i + 1) + ".png"
            );
        }
    }

    public RenderVariantPig() {
        super(new ModelPig(), new ModelPig(), 0.7F); // ← FIX
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // 0–5 (6 total textures including vanilla)
        int variant = Math.abs(entity.getEntityId()) % 6;

        // 0 = vanilla pig texture
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–5 = custom textures
        return CUSTOM_TEXTURES[variant - 1];
    }
}
