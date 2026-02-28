package com.astryxion.mobvariants.event;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ZombieVariantEvents {

    private static final int VARIANT_COUNT = 8;

    @SubscribeEvent
    public static void onZombieSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityZombie)) return;

        EntityZombie zombie = (EntityZombie) event.getEntity();

        if (!zombie.getEntityData().hasKey("Variant")) {
            zombie.getEntityData().setInteger(
                "Variant",
                zombie.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
