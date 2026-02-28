package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

public class VariantChickenRenderer extends ChickenRenderer {

    public VariantChickenRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(ChickenEntity chicken) {
        int variant = chicken.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla chicken
        if (variant == 0) {
            return super.getTextureLocation(chicken);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/chicken/chicken_" + variant + ".png"
        );
    }
}
