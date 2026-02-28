package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class VariantOcelotRenderer extends RenderOcelot {

    public VariantOcelotRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityOcelot ocelot) {
        int variant = ocelot.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(ocelot);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/ocelot/ocelot_" + variant + ".png"
        );
    }
}
