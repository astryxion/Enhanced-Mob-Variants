package com.astryxion.emv;

import net.minecraft.world.entity.Entity;

public final class Registration {
    private Registration() {}

    public static void init() {
        // Variant data is stored on Mob via mixin + synced entity data.
    }

    public static boolean has(Entity entity) {
        return entity instanceof EmvVariantHolder holder && holder.emv$hasVariant();
    }

    public static int get(Entity entity) {
        if (entity instanceof EmvVariantHolder holder && holder.emv$hasVariant()) {
            return holder.emv$getVariant();
        }
        return 0;
    }

    public static void set(Entity entity, int variant) {
        if (entity instanceof EmvVariantHolder holder) {
            holder.emv$setVariant(variant);
        }
    }
}
