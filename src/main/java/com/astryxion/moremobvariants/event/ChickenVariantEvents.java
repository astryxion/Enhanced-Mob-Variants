package com.astryxion.moremobvariants.event;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ChickenVariantEvents {

    /** 0 vanilla; 1–7 match {@code chicken_1.png} … {@code chicken_7.png} */
    private static final int VARIANT_COUNT = 8;

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
