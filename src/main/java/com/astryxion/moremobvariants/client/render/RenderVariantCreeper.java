package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderVariantCreeper extends RenderCreeper {

    /** creeper_1.png … creeper_4.png; entity id % 5 == 0 uses vanilla. */
    private static final int CUSTOM_CREEPER_COUNT = 4;

    public RenderVariantCreeper() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        int variant = Math.abs(entity.getEntityId()) % (CUSTOM_CREEPER_COUNT + 1);

        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/creeper/creeper_" + variant + ".png"
        );
    }
}
