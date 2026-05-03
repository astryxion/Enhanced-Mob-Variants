package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

public class VariantSkeletonRenderer extends RenderBiped<EntitySkeleton> {

    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantSkeletonRenderer(RenderManager manager) {
        super(manager, new ModelSkeleton(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySkeleton skeleton) {
        int variant = skeleton.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return new ResourceLocation("textures/entity/skeleton/skeleton.png");
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/skeleton/skeleton_" + variant + ".png");
    }
}
