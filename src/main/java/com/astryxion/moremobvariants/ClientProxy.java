package com.astryxion.moremobvariants;

import com.astryxion.moremobvariants.client.render.RenderVariantCow;
import com.astryxion.moremobvariants.client.render.RenderVariantPig;
import com.astryxion.moremobvariants.client.render.RenderVariantChicken;
import com.astryxion.moremobvariants.client.render.RenderVariantSheep;
import com.astryxion.moremobvariants.client.render.RenderVariantSkeleton;
import com.astryxion.moremobvariants.client.render.RenderVariantSpider;
import com.astryxion.moremobvariants.client.render.RenderVariantWolf;
import com.astryxion.moremobvariants.client.render.RenderVariantZombie;

import cpw.mods.fml.client.registry.RenderingRegistry;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;

import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {

        // Passive mobs
        RenderingRegistry.registerEntityRenderingHandler(
            EntityCow.class,
            new RenderVariantCow()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntityPig.class,
            new RenderVariantPig()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntityChicken.class,
            new RenderVariantChicken()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntitySheep.class,
            new RenderVariantSheep()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntityWolf.class,
            new RenderVariantWolf()
        );

        // Hostile mobs
        RenderingRegistry.registerEntityRenderingHandler(
            EntitySkeleton.class,
            new RenderVariantSkeleton()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntitySpider.class,
            new RenderVariantSpider()
        );

        RenderingRegistry.registerEntityRenderingHandler(
            EntityZombie.class,
            new RenderVariantZombie()
        );
    }
}
