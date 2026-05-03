package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.event.SheepVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;

public class VariantSheepRenderer extends SheepRenderer {

    public VariantSheepRenderer(EntityRendererManager manager) {
        super(manager);
        this.layers.removeIf(layer -> layer instanceof SheepWoolLayer);
        this.addLayer(new VariantLayerSheepWool(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SheepEntity sheep) {
        int variant = sheep.getPersistentData().getInt("Variant");
        if (variant <= 0) {
            return super.getTextureLocation(sheep);
        }
        String suffix = SheepVariants.textureSuffix(variant);
        if (suffix == null) {
            return super.getTextureLocation(sheep);
        }
        return new ResourceLocation(SheepVariants.ASSET_NAMESPACE, "textures/entity/sheep/" + suffix + ".png");
    }
}
