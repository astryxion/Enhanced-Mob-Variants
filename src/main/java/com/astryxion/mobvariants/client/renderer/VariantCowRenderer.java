package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;

public class VariantCowRenderer extends CowRenderer {

    public VariantCowRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(CowEntity cow) {
        int variant = cow.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla cow texture
        if (variant == 0) {
            return super.getTextureLocation(cow);
        }

        // Variants 1–9 = custom textures
        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/cow/cow_" + variant + ".png"
        );
    }
}
