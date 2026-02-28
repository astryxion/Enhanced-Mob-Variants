package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantSkeleton extends RenderSkeleton {

    // 4 custom textures: skeleton_1 → skeleton_4
    private static final ResourceLocation[] CUSTOM_TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < 4; i++) {
            CUSTOM_TEXTURES[i] = new ResourceLocation(
                "moremobvariants",
                "textures/entity/skeleton/skeleton_" + (i + 1) + ".png"
            );
        }
    }

    // IMPORTANT: RenderSkeleton has a NO-ARG constructor in 1.7.10
    public RenderVariantSkeleton() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // 0–4 (5 total including vanilla)
        int variant = Math.abs(entity.getEntityId()) % 5;

        // 0 = vanilla skeleton
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–4 = custom skeleton textures
        return CUSTOM_TEXTURES[variant - 1];
    }
}
