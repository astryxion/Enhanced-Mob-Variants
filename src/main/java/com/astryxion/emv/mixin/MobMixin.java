package com.astryxion.emv.mixin;

import com.astryxion.emv.EventHandler;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
public class MobMixin {
    @ModifyVariable(method = "finalizeSpawn", at = @At("HEAD"), argsOnly = true)
    private SpawnGroupData emv$sharePackVariant(
        SpawnGroupData groupData,
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        EntitySpawnReason spawnReason
    ) {
        return EventHandler.onFinalizeSpawn((Mob) (Object) this, spawnReason, groupData);
    }
}
