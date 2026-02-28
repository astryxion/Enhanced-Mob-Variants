package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class VariantCowRenderer extends RenderCow {

    public VariantCowRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCow cow) {
        int variant = cow.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(cow);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/cow/cow_" + variant + ".png"
        );
    }
}
