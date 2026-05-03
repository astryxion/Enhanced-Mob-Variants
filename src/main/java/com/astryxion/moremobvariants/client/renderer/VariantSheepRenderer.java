package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.event.SheepVariants;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class VariantSheepRenderer extends RenderSheep {

    private static final ResourceLocation VANILLA_SHEEP = new ResourceLocation("textures/entity/sheep/sheep.png");

    public VariantSheepRenderer(RenderManager manager) {
        super(manager);
        this.layerRenderers.clear();
        this.addLayer(new VariantLayerSheepWool(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySheep sheep) {
        int variant = sheep.getEntityData().getInteger("Variant");
        if (variant <= 0) {
            return VANILLA_SHEEP;
        }
        String suffix = SheepVariants.textureSuffix(variant);
        if (suffix == null) {
            return VANILLA_SHEEP;
        }
        return new ResourceLocation(SheepVariants.ASSET_NAMESPACE, "textures/entity/sheep/" + suffix + ".png");
    }
}
