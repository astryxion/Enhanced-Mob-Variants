package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;

public class VariantPigRenderer extends PigRenderer {

    public VariantPigRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(PigEntity pig) {
        int variant = pig.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla pig
        if (variant == 0) {
            return super.getTextureLocation(pig);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/pig/pig_" + variant + ".png"
        );
    }
}
