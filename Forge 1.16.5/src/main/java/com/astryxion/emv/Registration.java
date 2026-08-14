package com.astryxion.emv;

import net.minecraft.entity.Entity;

public final class Registration {
    private Registration() {}

    public static boolean has(Entity entity) {
        if (entity instanceof EmvVariantHolder) {
            return ((EmvVariantHolder) entity).emv$hasVariant();
        }
        return false;
    }

    public static int get(Entity entity) {
        if (entity instanceof EmvVariantHolder) {
            EmvVariantHolder holder = (EmvVariantHolder) entity;
            if (holder.emv$hasVariant()) {
                return holder.emv$getVariant();
            }
        }
        return 0;
    }

    public static void set(Entity entity, int variant) {
        if (entity instanceof EmvVariantHolder) {
            ((EmvVariantHolder) entity).emv$setVariant(variant);
        }
    }
}
