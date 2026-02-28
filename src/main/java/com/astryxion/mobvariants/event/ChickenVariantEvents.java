package com.astryxion.mobvariants.event;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ChickenVariantEvents {

    private static final int VARIANT_COUNT = 7;

    @SubscribeEvent
    public static void onChickenSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityChicken)) return;

        EntityChicken chicken = (EntityChicken) event.getEntity();

        if (!chicken.getEntityData().hasKey("Variant")) {
            chicken.getEntityData().setInteger(
                "Variant",
                chicken.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
