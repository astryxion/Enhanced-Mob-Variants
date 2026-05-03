package com.astryxion.moremobvariants.event;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SpiderVariantEvents {

    /** 0 vanilla; 1–4 match {@code spider_1.png} … {@code spider_4.png} */
    private static final int VARIANT_COUNT = 5;

    @SubscribeEvent
    public static void onSpiderSpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntitySpider)) return;

        EntitySpider spider = (EntitySpider) event.getEntity();

        if (!spider.getEntityData().hasKey("Variant")) {
            spider.getEntityData().setInteger(
                "Variant",
                spider.world.rand.nextInt(VARIANT_COUNT)
            );
        }
    }
}
