package com.astryxion.moremobvariants;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(
    modid = "moremobvariants",
    name = "More Mob Variants",
    version = "1.0"
)
public class MoreMobVariants {

    @Instance("moremobvariants")
    public static MoreMobVariants instance;

    @SidedProxy(
        clientSide = "com.astryxion.moremobvariants.ClientProxy",
        serverSide = "com.astryxion.moremobvariants.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Renderer-only registration (safe on server)
        proxy.registerRenderers();
    }
}
