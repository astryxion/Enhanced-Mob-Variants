package com.astryxion.mobvariants.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VariantSpawnEvents {

    private static final int COW_VARIANTS = 10;
    private static final int CAT_VARIANTS = 5;
    private static final int CHICKEN_VARIANTS = 8;
    private static final int PIG_VARIANTS = 6;
    private static final int SKELETON_VARIANTS = 5;
    private static final int SPIDER_VARIANTS = 5;
    private static final int ZOMBIE_VARIANTS = 9;
    private static final int WOLF_VARIANTS = 15;

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        LivingEntity entity = (LivingEntity) event.getEntity();

        if (entity instanceof CowEntity) assign(entity, COW_VARIANTS);
        if (entity instanceof CatEntity) assign(entity, CAT_VARIANTS);
        if (entity instanceof ChickenEntity) assign(entity, CHICKEN_VARIANTS);
        if (entity instanceof PigEntity) assign(entity, PIG_VARIANTS);
        if (entity instanceof AbstractSkeletonEntity) assign(entity, SKELETON_VARIANTS);
        if (entity instanceof SpiderEntity) assign(entity, SPIDER_VARIANTS);
        if (entity instanceof ZombieEntity) assign(entity, ZOMBIE_VARIANTS);
        if (entity instanceof WolfEntity) assign(entity, WOLF_VARIANTS);
    }

    private static void assign(LivingEntity entity, int max) {
        CompoundNBT tag = entity.getPersistentData();
        if (!tag.contains("Variant")) {
            tag.putInt("Variant", entity.getRandom().nextInt(max));
        }
    }
}
