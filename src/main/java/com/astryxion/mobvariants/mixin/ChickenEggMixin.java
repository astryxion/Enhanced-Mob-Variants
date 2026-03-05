package com.astryxion.mobvariants.mixin;

import com.astryxion.mobvariants.MoreMobVariants;
import com.astryxion.mobvariants.config.Variants;
import com.astryxion.mobvariants.variant.MobVariant;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EggEntity.class)
public class ChickenEggMixin {
    @ModifyVariable(
            method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V",
            at = @At("STORE")
    )
    private ChickenEntity mixin(ChickenEntity chickenEntity) {
        MobVariant variant = Variants.getRandomVariant(EntityType.CHICKEN, chickenEntity.getRandom().nextLong(), chickenEntity.getWorld().getBiome(chickenEntity.getBlockPos()), null, chickenEntity.getWorld().getMoonSize());

        NbtCompound newNbt = new NbtCompound();
        chickenEntity.writeNbt(newNbt);
        newNbt.putString(MoreMobVariants.NBT_KEY, variant.getIdentifier().toString());
        chickenEntity.readCustomDataFromNbt(newNbt);

        return chickenEntity;
    }
}
