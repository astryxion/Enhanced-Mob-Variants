package com.astryxion.moremobvariants.client.renderer;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class VariantZombieRenderer extends RenderBiped<EntityZombie> {

    private static final String TEX_NAMESPACE = "moremobvariants";

    public VariantZombieRenderer(RenderManager manager) {
        super(manager, new ModelZombie(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityZombie zombie) {
        int variant = zombie.getEntityData().getInteger("Variant");

        if (variant == 0) {
            return new ResourceLocation("textures/entity/zombie/zombie.png");
        }

        return new ResourceLocation(TEX_NAMESPACE, "textures/entity/zombie/zombie_" + variant + ".png");
    }
}
