package com.astryxion.mobvariants.event;

import net.minecraft.entity.passive.EntityCow;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CowVariantEvents {

    private static final int VARIANT_COUNT = 10;

    @SubscribeEvent
    public static void onCowSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityCow)) return;

        EntityCow cow = (EntityCow) event.getEntity();

        if (!cow.getEntityData().hasKey("Variant")) {
            cow.getEntityData().setInteger(
                "Variant",
                cow.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
