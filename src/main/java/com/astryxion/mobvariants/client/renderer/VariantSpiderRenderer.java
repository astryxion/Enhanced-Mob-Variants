package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class VariantSpiderRenderer extends SpiderRenderer<SpiderEntity> {

    public VariantSpiderRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderEntity spider) {
        int variant = spider.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla spider
        if (variant == 0) {
            return super.getTextureLocation(spider);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/spider/spider_" + variant + ".png"
        );
    }
}
