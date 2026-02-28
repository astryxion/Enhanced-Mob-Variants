package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantZombie extends RenderZombie {

    private static final String[] SKINS = {
        "alex",
        "ari",
        "efe",
        "kai",
        "makena",
        "noor",
        "sunny",
        "zuri"
    };

    // RenderZombie is NO-ARG in 1.7.10
    public RenderVariantZombie() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // 0–8 (9 total including vanilla)
        int variant = Math.abs(entity.getEntityId()) % (SKINS.length + 1);

        // 0 = vanilla zombie
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–8 = custom zombie skins
        String skin = SKINS[variant - 1];

        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/zombie/" + skin + ".png"
        );
    }
}
