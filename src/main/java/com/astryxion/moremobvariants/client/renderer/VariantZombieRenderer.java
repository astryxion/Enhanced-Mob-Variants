package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.MoreMobVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class VariantZombieRenderer extends ZombieRenderer {

    public VariantZombieRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieEntity zombie) {
        int variant = zombie.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla zombie
        if (variant == 0) {
            return super.getTextureLocation(zombie);
        }

        return new ResourceLocation(
            MoreMobVariants.MODID,
            "textures/entity/zombie/zombie_" + variant + ".png"
        );
    }
}
