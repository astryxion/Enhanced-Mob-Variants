package com.astryxion.emv;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancedMobVariants implements ModInitializer {
    public static final String MODID = "emv";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        Config.loadCommon();
        Registration.init();
        ServerEntityEvents.ENTITY_LOAD.register(EventHandler::onEntityLoad);
        ServerLifecycleEvents.SERVER_STARTING.register(server -> Config.reloadCommonIfChanged());
        ServerTickEvents.END_SERVER_TICK.register(server -> EventHandler.endServerTick());
        LOGGER.info("Enhanced Mob Variants initialized with Mod ID: {}", MODID);
    }
}
