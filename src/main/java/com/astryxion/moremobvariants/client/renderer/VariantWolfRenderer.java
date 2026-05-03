package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.event.WolfVariants;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class VariantWolfRenderer extends RenderWolf {

    private static final ResourceLocation VANILLA_WILD =
        new ResourceLocation("textures/entity/wolf/wolf.png");
    private static final ResourceLocation VANILLA_TAME =
        new ResourceLocation("textures/entity/wolf/wolf_tame.png");
    private static final ResourceLocation VANILLA_ANGRY =
        new ResourceLocation("textures/entity/wolf/wolf_angry.png");

    public VariantWolfRenderer(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWolf wolf) {
        int variant = wolf.getEntityData().getInteger("Variant");

        if (variant <= 0 || variant >= WolfVariants.VARIANT_COUNT || WolfVariants.textureSuffix(variant) == null) {
            return vanillaTexture(wolf);
        }

        return WolfVariants.textureForWolf(wolf, variant);
    }

    private static ResourceLocation vanillaTexture(EntityWolf wolf) {
        if (wolf.isTamed()) {
            return VANILLA_TAME;
        }
        if (wolf.isAngry()) {
            return VANILLA_ANGRY;
        }
        return VANILLA_WILD;
    }
}
