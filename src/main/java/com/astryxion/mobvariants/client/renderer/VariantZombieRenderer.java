package com.astryxion.mobvariants.client.renderer;

import com.astryxion.mobvariants.AstryxionsMobVariants;
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
            AstryxionsMobVariants.MODID,
            "textures/entity/zombie/zombie_" + variant + ".png"
        );
    }
}
