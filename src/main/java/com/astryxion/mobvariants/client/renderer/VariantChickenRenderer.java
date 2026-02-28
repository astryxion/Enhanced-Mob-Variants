package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

public class VariantChickenRenderer extends RenderChicken {

    public VariantChickenRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityChicken chicken) {
        int variant = chicken.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(chicken);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/chicken/chicken_" + variant + ".png"
        );
    }
}
