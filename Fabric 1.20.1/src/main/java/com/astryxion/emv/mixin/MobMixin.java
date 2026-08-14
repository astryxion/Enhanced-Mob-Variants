package com.astryxion.emv.mixin;

import com.astryxion.emv.EmvVariantHolder;
import com.astryxion.emv.EventHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EmvVariantHolder {
    @Unique
    private static final EntityDataAccessor<Integer> EMV_VARIANT =
        SynchedEntityData.defineId(Mob.class, EntityDataSerializers.INT);

    @Unique
    private static final String EMV_VARIANT_KEY = "emv.variant";

    protected MobMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void emv$defineVariant(CallbackInfo ci) {
        this.entityData.define(EMV_VARIANT, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void emv$saveVariant(CompoundTag tag, CallbackInfo ci) {
        int variant = this.entityData.get(EMV_VARIANT);
        if (variant >= 0) {
            tag.putInt(EMV_VARIANT_KEY, variant);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void emv$loadVariant(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains(EMV_VARIANT_KEY)) {
            this.entityData.set(EMV_VARIANT, tag.getInt(EMV_VARIANT_KEY));
        }
    }

    @ModifyVariable(method = "finalizeSpawn", at = @At("HEAD"), argsOnly = true)
    private SpawnGroupData emv$sharePackVariant(
        SpawnGroupData groupData,
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType spawnReason,
        SpawnGroupData spawnGroupData,
        @Nullable CompoundTag spawnTag
    ) {
        return EventHandler.onFinalizeSpawn((Mob) (Object) this, spawnReason, groupData);
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
