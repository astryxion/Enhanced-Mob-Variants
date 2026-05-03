package com.astryxion.moremobvariants.client.render;

import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderVariantZombie extends RenderZombie {

    /** Custom skins are zombie_1.png … zombie_8.png; entity id % 9 == 0 uses vanilla. */
    private static final int CUSTOM_ZOMBIE_COUNT = 8;

    // RenderZombie is NO-ARG in 1.7.10
    public RenderVariantZombie() {
        super();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        // Pigmen subclass EntityZombie; client render lookup can pick this renderer for them.
        if (entity instanceof EntityPigZombie) {
            return super.getEntityTexture(entity);
        }

        // Zombie villagers share EntityZombie + this renderer; vanilla uses different textures/UVs.
        if (entity instanceof EntityZombie && ((EntityZombie) entity).isVillager()) {
            return super.getEntityTexture(entity);
        }

        int variant = Math.abs(entity.getEntityId()) % (CUSTOM_ZOMBIE_COUNT + 1);

        if (variant == 0) {
            return super.getEntityTexture(entity);
        }

        return new ResourceLocation(
            "moremobvariants",
            "textures/entity/zombie/zombie_" + variant + ".png"
        );
    }
}
