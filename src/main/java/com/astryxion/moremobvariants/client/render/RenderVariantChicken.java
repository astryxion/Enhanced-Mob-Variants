package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantChicken extends RenderChicken {

    // 7 custom textures: chicken_1 → chicken_7
    private static final ResourceLocation[] CUSTOM_TEXTURES = new ResourceLocation[7];

    static {
        for (int i = 0; i < 7; i++) {
            CUSTOM_TEXTURES[i] = new ResourceLocation(
                "moremobvariants",
                "textures/entity/chicken/chicken_" + (i + 1) + ".png"
            );
        }
    }

    public RenderVariantChicken() {
        super(new ModelChicken(), 0.3F); // vanilla chicken shadow size
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // 0–7 (8 total including vanilla)
        int variant = Math.abs(entity.getEntityId()) % 8;

        // 0 = vanilla chicken
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–7 = custom textures
        return CUSTOM_TEXTURES[variant - 1];
    }
}
