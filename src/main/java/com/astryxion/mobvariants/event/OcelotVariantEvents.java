package com.astryxion.mobvariants.event;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class OcelotVariantEvents {

    private static final int VARIANT_COUNT = 5;

    @SubscribeEvent
    public static void onOcelotSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityOcelot)) return;

        EntityOcelot ocelot = (EntityOcelot) event.getEntity();

        if (!ocelot.getEntityData().hasKey("Variant")) {
            ocelot.getEntityData().setInteger(
                "Variant",
                ocelot.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
