package com.astryxion.moremobvariants.event;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SheepVariantEvents {

    @SubscribeEvent
    public static void onSheepSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntitySheep)) {
            return;
        }

        EntitySheep sheep = (EntitySheep) event.getEntity();

        if (!sheep.getEntityData().hasKey("Variant")) {
            sheep.getEntityData().setInteger(
                "Variant",
                sheep.world.rand.nextInt(SheepVariants.VARIANT_COUNT)
            );
        }
    }
}
