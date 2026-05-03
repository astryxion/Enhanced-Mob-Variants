package com.astryxion.moremobvariants.event;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class WolfVariantEvents {

    @SubscribeEvent
    public static void onWolfSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityWolf)) return;

        EntityWolf wolf = (EntityWolf) event.getEntity();

        if (!wolf.getEntityData().hasKey("Variant")) {
            wolf.getEntityData().setInteger(
                "Variant",
                wolf.world.rand.nextInt(WolfVariants.VARIANT_COUNT)
            );
        }
    }
}
