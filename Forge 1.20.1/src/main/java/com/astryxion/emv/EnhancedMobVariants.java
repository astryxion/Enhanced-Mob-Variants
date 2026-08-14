package com.astryxion.emv;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(EnhancedMobVariants.MODID)
public class EnhancedMobVariants {
    public static final String MODID = "emv";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EnhancedMobVariants() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        LOGGER.info("Enhanced Mob Variants initialized with Mod ID: {}", MODID);
    }
}
