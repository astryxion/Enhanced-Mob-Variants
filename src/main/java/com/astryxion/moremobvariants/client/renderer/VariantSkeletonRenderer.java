package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.MoreMobVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class VariantSkeletonRenderer extends SkeletonRenderer {

    public VariantSkeletonRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeletonEntity skeleton) {
        int variant = skeleton.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla skeleton
        if (variant == 0) {
            return super.getTextureLocation(skeleton);
        }

        return new ResourceLocation(
            MoreMobVariants.MODID,
            "textures/entity/skeleton/skeleton_" + variant + ".png"
        );
    }
}
