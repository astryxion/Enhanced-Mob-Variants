package com.astryxion.moremobvariants.client.renderer;

import com.astryxion.moremobvariants.event.SheepVariants;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.SheepWoolLayer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Replaces vanilla wool layer so variant sheep can bind separate wool PNGs,
 * while delegating variant 0 to vanilla {@link SheepWoolLayer} (same idea as 1.12.2).
 */
public class VariantLayerSheepWool extends LayerRenderer<SheepEntity, SheepModel<SheepEntity>> {

    private final SheepWoolLayer vanillaWool;
    private final SheepWoolModel<SheepEntity> woolModel = new SheepWoolModel<>();

    public VariantLayerSheepWool(VariantSheepRenderer sheepRenderer) {
        super(sheepRenderer);
        this.vanillaWool = new SheepWoolLayer(sheepRenderer);
    }

    @Override
    public void render(
        MatrixStack matrixStackIn,
        IRenderTypeBuffer bufferIn,
        int packedLightIn,
        SheepEntity sheep,
        float limbSwing,
        float limbSwingAmount,
        float partialTicks,
        float ageInTicks,
        float netHeadYaw,
        float headPitch
    ) {
        int variant = sheep.getPersistentData().getInt("Variant");
        if (variant <= 0 || SheepVariants.textureSuffix(variant) == null) {
            vanillaWool.render(
                matrixStackIn,
                bufferIn,
                packedLightIn,
                sheep,
                limbSwing,
                limbSwingAmount,
                partialTicks,
                ageInTicks,
                netHeadYaw,
                headPitch
            );
            return;
        }

        if (sheep.isSheared() || sheep.isInvisible()) {
            return;
        }

        ResourceLocation woolLoc = SheepVariants.woolTexture(variant);
        if (woolLoc == null) {
            return;
        }

        IVertexBuilder builder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(woolLoc));
        this.getParentModel().copyPropertiesTo(this.woolModel);
        this.woolModel.prepareMobModel(sheep, limbSwing, limbSwingAmount, partialTicks);
        this.woolModel.setupAnim(sheep, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        int overlay = LivingRenderer.getOverlayCoords(sheep, 0.0F);
        this.woolModel.renderToBuffer(matrixStackIn, builder, packedLightIn, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
