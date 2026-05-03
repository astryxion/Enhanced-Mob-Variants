package com.astryxion.moremobvariants.event;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CreeperVariantEvents {

    /** 0 vanilla; 1–4 match {@code creeper_1.png} … {@code creeper_4.png} */
    private static final int VARIANT_COUNT = 5;

    @SubscribeEvent
    public static void onCreeperSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityCreeper)) return;

        EntityCreeper creeper = (EntityCreeper) event.getEntity();

        if (!creeper.getEntityData().hasKey("Variant")) {
            creeper.getEntityData().setInteger(
                "Variant",
                creeper.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
