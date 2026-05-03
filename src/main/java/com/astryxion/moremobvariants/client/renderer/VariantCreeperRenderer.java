package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class VariantCreeperRenderer extends RenderCreeper {

    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantCreeperRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper creeper) {
        int variant = creeper.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(creeper);
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/creeper/creeper_" + variant + ".png");
    }
}
