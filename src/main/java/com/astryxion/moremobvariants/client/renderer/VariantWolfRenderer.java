package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.MoreMobVariants;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class VariantWolfRenderer extends WolfRenderer {

    public VariantWolfRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(WolfEntity wolf) {
        int variant = wolf.getPersistentData().getInt("Variant");

        // Variant 0 = vanilla wolf textures
        if (variant == 0) {
            return super.getTextureLocation(wolf);
        }

        String state;
        if (wolf.isAngry()) {
            state = "angry";
        } else if (wolf.isTame()) {
            state = "tame";
        } else {
            state = "wild";
        }

        return new ResourceLocation(
            MoreMobVariants.MODID,
            "textures/entity/wolf/wolf_" + variant + "_" + state + ".png"
        );
    }
}
