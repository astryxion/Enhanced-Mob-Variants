package com.astryxion.emv.mixin;

import com.astryxion.emv.EmvVariantHolder;
import com.astryxion.emv.EventHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(MobEntity.class)
public abstract class MobMixin extends LivingEntity implements EmvVariantHolder {
    @Unique
    private static final DataParameter<Integer> EMV_VARIANT =
        EntityDataManager.defineId(MobEntity.class, DataSerializers.INT);

    @Unique
    private static final String EMV_VARIANT_KEY = "emv.variant";

    protected MobMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void emv$defineVariant(CallbackInfo ci) {
        this.entityData.define(EMV_VARIANT, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void emv$saveVariant(CompoundNBT tag, CallbackInfo ci) {
        int variant = this.entityData.get(EMV_VARIANT);
        if (variant >= 0) {
            tag.putInt(EMV_VARIANT_KEY, variant);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void emv$loadVariant(CompoundNBT tag, CallbackInfo ci) {
        if (tag.contains(EMV_VARIANT_KEY)) {
            this.entityData.set(EMV_VARIANT, tag.getInt(EMV_VARIANT_KEY));
        }
    }

    @ModifyVariable(method = "finalizeSpawn", at = @At("HEAD"), argsOnly = true)
    private ILivingEntityData emv$sharePackVariant(
        ILivingEntityData groupData,
        IServerWorld world,
        DifficultyInstance difficulty,
        SpawnReason spawnReason,
        ILivingEntityData spawnGroupData,
        @Nullable CompoundNBT spawnTag
    ) {
        return EventHandler.onFinalizeSpawn((MobEntity) (Object) this, spawnReason, groupData);
    }

    @Override
    public int emv$getVariant() {
        return this.entityData.get(EMV_VARIANT);
    }

    @Override
    public void emv$setVariant(int variant) {
        this.entityData.set(EMV_VARIANT, variant);
    }

    @Override
    public boolean emv$hasVariant() {
        return this.entityData.get(EMV_VARIANT) >= 0;
    }
}
