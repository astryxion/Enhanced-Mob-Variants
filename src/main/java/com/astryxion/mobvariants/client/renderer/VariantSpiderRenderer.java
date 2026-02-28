package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class VariantSpiderRenderer extends RenderSpider<EntitySpider> {

    public VariantSpiderRenderer(RenderManager manager) {
        super(manager);
    }

    protected ResourceLocation getEntityTexture(EntitySpider spider) {
        int variant = spider.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(spider);
        }

        return new ResourceLocation(
            AstryxionsMobVariants.MODID,
            "textures/entity/spider/spider_" + variant + ".png"
        );
    }
}
