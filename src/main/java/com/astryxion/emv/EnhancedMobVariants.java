package com.astryxion.emv;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = EnhancedMobVariants.MODID,
    name = "Enhanced Mob Variants",
    version = EnhancedMobVariants.VERSION,
    acceptedMinecraftVersions = "[1.7.10]",
    guiFactory = "com.astryxion.emv.EmvGuiFactory"
)
public class EnhancedMobVariants {
    public static final String MODID = "emv";
    public static final String VERSION = "1.7.10-1.3.0";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event.getModConfigurationDirectory());
        EmvNetwork.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        if (event.getSide() == Side.CLIENT) {
            ClientHandler.registerRenderers();
        }
        LOGGER.info("Enhanced Mob Variants initialized with Mod ID: {}", MODID);
    }
}
