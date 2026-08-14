package com.astryxion.emv;

import net.minecraft.entity.Entity;

public final class Registration {
    static final String KEY = "emv.variant";

    private Registration() {}

    public static boolean has(Entity entity) {
        return entity != null && entity.getEntityData().hasKey(KEY);
    }

    public static int get(Entity entity) {
        if (!has(entity)) {
            return 0;
        }
        return entity.getEntityData().getInteger(KEY);
    }

    public static void set(Entity entity, int variant) {
        if (entity == null) {
            return;
        }
        entity.getEntityData().setInteger(KEY, variant);
        if (!entity.world.isRemote) {
            EmvNetwork.sync(entity);
        }
    }

    static void applyClient(Entity entity, int variant) {
        entity.getEntityData().setInteger(KEY, variant);
    }
}
