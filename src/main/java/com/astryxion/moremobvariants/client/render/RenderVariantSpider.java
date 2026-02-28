package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantSpider extends RenderSpider {

    // 4 custom textures: spider_1 → spider_4
    private static final ResourceLocation[] CUSTOM_TEXTURES = new ResourceLocation[4];

    static {
        for (int i = 0; i < 4; i++) {
            CUSTOM_TEXTURES[i] = new ResourceLocation(
                "moremobvariants",
                "textures/entity/spider/spider_" + (i + 1) + ".png"
            );
        }
    }

    // IMPORTANT: RenderSpider has a NO-ARG constructor in 1.7.10
    public RenderVariantSpider() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {

        // 0–4 (5 total including vanilla)
        int variant = Math.abs(entity.getEntityId()) % 5;

        // 0 = vanilla spider texture
        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        // 1–4 = custom spider textures
        return CUSTOM_TEXTURES[variant - 1];
    }
}
