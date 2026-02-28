package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
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
            AstryxionsMobVariants.MODID,
            "textures/entity/skeleton/skeleton_" + variant + ".png"
        );
    }
}
