package com.astryxion.moremobvariants;

import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Husk;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class EventHandler {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Chicken e && !e.hasData(Registration.CHICKEN_VARIANT)) {
            e.setData(Registration.CHICKEN_VARIANT, e.getRandom().nextInt(8));
        }
        if (event.getEntity() instanceof Cow e && !e.hasData(Registration.COW_VARIANT)) {
            e.setData(Registration.COW_VARIANT, e.getRandom().nextInt(10));
        }
        if (event.getEntity() instanceof Cat e && !e.hasData(Registration.CAT_VARIANT)) {
            e.setData(Registration.CAT_VARIANT, e.getRandom().nextInt(6));
        }
        if (event.getEntity() instanceof Pig e && !e.hasData(Registration.PIG_VARIANT)) {
            e.setData(Registration.PIG_VARIANT, e.getRandom().nextInt(6));
        }
        if (event.getEntity() instanceof Skeleton e && !e.hasData(Registration.SKELETON_VARIANT)) {
            e.setData(Registration.SKELETON_VARIANT, e.getRandom().nextInt(5));
        }
        if (event.getEntity() instanceof Spider e && !(e instanceof CaveSpider) && !e.hasData(Registration.SPIDER_VARIANT)) {
            e.setData(Registration.SPIDER_VARIANT, e.getRandom().nextInt(5));
        }
        if (event.getEntity() instanceof Zombie e && !(e instanceof Husk) && !e.hasData(Registration.ZOMBIE_VARIANT)) {
            e.setData(Registration.ZOMBIE_VARIANT, e.getRandom().nextInt(9));
        }

        // Handle Sheep (7 total: 0 is vanilla, 1-6 are your custom textures)
        if (event.getEntity() instanceof Sheep e && !e.hasData(Registration.SHEEP_VARIANT)) {
            e.setData(Registration.SHEEP_VARIANT, e.getRandom().nextInt(7));
        }
        // Handle Wolf (8 outcomes: 0 vanilla, 1-7 custom breeds under textures/entity/wolf/)
        if (event.getEntity() instanceof Wolf e && !e.hasData(Registration.WOLF_VARIANT)) {
            e.setData(Registration.WOLF_VARIANT, e.getRandom().nextInt(8));
        }
    }
}
