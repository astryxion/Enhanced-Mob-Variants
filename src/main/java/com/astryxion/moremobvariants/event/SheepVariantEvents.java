package com.astryxion.moremobvariants.event;

import com.astryxion.moremobvariants.MoreMobVariants;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreMobVariants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SheepVariantEvents {

    @SubscribeEvent
    public static void onSheepSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof SheepEntity)) {
            return;
        }

        SheepEntity sheep = (SheepEntity) event.getEntity();

        if (!sheep.getPersistentData().contains("Variant")) {
            sheep.getPersistentData().putInt("Variant", sheep.getRandom().nextInt(SheepVariants.VARIANT_COUNT));
        }
    }
}
