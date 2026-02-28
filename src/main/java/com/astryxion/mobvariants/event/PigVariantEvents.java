package com.astryxion.mobvariants.event;

import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PigVariantEvents {

    private static final int VARIANT_COUNT = 5;

    @SubscribeEvent
    public static void onPigSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPig)) return;

        EntityPig pig = (EntityPig) event.getEntity();

        if (!pig.getEntityData().hasKey("Variant")) {
            pig.getEntityData().setInteger(
                "Variant",
                pig.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
