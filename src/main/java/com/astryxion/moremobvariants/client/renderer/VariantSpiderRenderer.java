package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class VariantSpiderRenderer extends RenderSpider<EntitySpider> {

    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantSpiderRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySpider spider) {
        int variant = spider.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return super.getEntityTexture(spider);
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/spider/spider_" + variant + ".png");
    }
}
