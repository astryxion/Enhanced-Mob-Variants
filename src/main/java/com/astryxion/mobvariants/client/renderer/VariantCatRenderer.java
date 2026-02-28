package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;

public class VariantCatRenderer extends CatRenderer {

    public VariantCatRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(CatEntity cat) {
        int variant = cat.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla cat texture
        if (variant == 0) {
            return super.getTextureLocation(cat);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/cat/cat_" + variant + ".png"
        );
    }
}
