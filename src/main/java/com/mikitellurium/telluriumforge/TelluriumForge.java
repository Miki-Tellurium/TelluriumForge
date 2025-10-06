package com.mikitellurium.telluriumforge;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = TelluriumForge.MOD_ID, name = TelluriumForge.NAME, version = TelluriumForge.VERSION)
public class TelluriumForge {
    public static final String MOD_ID = "telluriumforge";
    public static final String NAME = "TelluriumForge";
    public static final String VERSION = "1.4.0-beta";

    private static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
