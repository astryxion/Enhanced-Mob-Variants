package com.astryxion.emv;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(EnhancedMobVariants.MODID)
public class EnhancedMobVariants {
    public static final String MODID = "emv";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EnhancedMobVariants(IEventBus modEventBus, ModContainer modContainer) {
        // 1. Register the Registration class (Data Attachments)
        Registration.ATTACHMENT_TYPES.register(modEventBus);

        // 2. Register the Config
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        // 3. Mod Loading Events
        modEventBus.addListener(this::commonSetup);

        // 4. Register the NeoForge Event Bus (for the EventHandler)
        NeoForge.EVENT_BUS.register(EventHandler.class);

        LOGGER.info("Enhanced Mob Variants initialized with Mod ID: {}", MODID);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Any setup that doesn't involve rendering or spawning
    }
}
