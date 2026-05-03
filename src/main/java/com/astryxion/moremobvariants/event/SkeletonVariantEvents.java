package com.astryxion.moremobvariants.event;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SkeletonVariantEvents {

    /** 0 vanilla; 1–4 match {@code skeleton_1.png} … {@code skeleton_4.png} */
    private static final int VARIANT_COUNT = 5;

    @SubscribeEvent
    public static void onSkeletonSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntitySkeleton)) return;

        EntitySkeleton skeleton = (EntitySkeleton) event.getEntity();

        if (!skeleton.getEntityData().hasKey("Variant")) {
            skeleton.getEntityData().setInteger(
                "Variant",
                skeleton.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
