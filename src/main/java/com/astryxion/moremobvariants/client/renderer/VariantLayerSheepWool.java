package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.event.SheepVariants;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

/**
 * Replaces vanilla wool layer so variant sheep can bind separate wool PNGs (1.20-style paths)
 * while delegating variant 0 to vanilla {@link LayerSheepWool}.
 */
public class VariantLayerSheepWool implements LayerRenderer<EntitySheep> {

    private final VariantSheepRenderer sheepRenderer;
    private final LayerSheepWool vanillaWool;
    private final ModelSheep1 woolModel = new ModelSheep1();

    public VariantLayerSheepWool(VariantSheepRenderer sheepRenderer) {
        this.sheepRenderer = sheepRenderer;
        this.vanillaWool = new LayerSheepWool(sheepRenderer);
    }

    @Override
    public void doRenderLayer(
        EntitySheep sheep,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch,
        float scale
    ) {
        int variant = sheep.getEntityData().getInteger("Variant");
        if (variant <= 0 || SheepVariants.textureSuffix(variant) == null) {
            vanillaWool.doRenderLayer(
                sheep,
                limbSwing,
                limbSwingAmount,
                partialTicks,
                ageInTicks,
                netHeadYaw,
                headPitch,
                scale
            );
            return;
        }

        if (sheep.getSheared() || sheep.isInvisible()) {
            return;
        }

        ResourceLocation woolLoc = SheepVariants.woolTexture(variant);
        if (woolLoc == null) {
            return;
        }

        this.sheepRenderer.bindTexture(woolLoc);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.woolModel.setModelAttributes(this.sheepRenderer.getMainModel());
        this.woolModel.setLivingAnimations(sheep, limbSwing, limbSwingAmount, partialTicks);
        this.woolModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, sheep);
        this.sheepRenderer.setLightmap(sheep);
        this.woolModel.render(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
