package com.astryxion.moremobvariants;

import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = MoreMobVariants.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandler {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Chicken e && !e.hasData(Registration.CHICKEN_VARIANT)) {
            e.setData(Registration.CHICKEN_VARIANT, e.getRandom().nextInt(8));
        }
        if (event.getEntity() instanceof Cow e && !e.hasData(Registration.COW_VARIANT)) {
            e.setData(Registration.COW_VARIANT, e.getRandom().nextInt(10));
        }
        if (event.getEntity() instanceof Pig e && !e.hasData(Registration.PIG_VARIANT)) {
            e.setData(Registration.PIG_VARIANT, e.getRandom().nextInt(6));
        }
        if (event.getEntity() instanceof Skeleton e && !e.hasData(Registration.SKELETON_VARIANT)) {
            e.setData(Registration.SKELETON_VARIANT, e.getRandom().nextInt(5));
        }
        if (event.getEntity() instanceof Spider e && !e.hasData(Registration.SPIDER_VARIANT)) {
            e.setData(Registration.SPIDER_VARIANT, e.getRandom().nextInt(5));
        }
        if (event.getEntity() instanceof Zombie e && !e.hasData(Registration.ZOMBIE_VARIANT)) {
            e.setData(Registration.ZOMBIE_VARIANT, e.getRandom().nextInt(9));
        }

        // Handle Sheep (7 total: 0 is vanilla, 1-6 are your custom textures)
        if (event.getEntity() instanceof Sheep e && !e.hasData(Registration.SHEEP_VARIANT)) {
            e.setData(Registration.SHEEP_VARIANT, e.getRandom().nextInt(7));
        }
    }
}