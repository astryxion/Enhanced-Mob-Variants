package com.astryxion.moremobvariants.event;

import net.minecraft.entity.passive.EntityPig;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PigVariantEvents {

    /** 0 vanilla; 1–5 match {@code pig_1.png} … {@code pig_5.png} */
    private static final int VARIANT_COUNT = 6;

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
