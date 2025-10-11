package com.mikitellurium.telluriumforge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TelluriumForge.MOD_ID, name = TelluriumForge.NAME, version = TelluriumForge.VERSION)
public class TelluriumForge {
    public static final String MOD_ID = "telluriumforge";
    public static final String NAME = "TelluriumForge";
    public static final String VERSION = "1.4.0-beta";
    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }

    public static String modId() {
        return MOD_ID;
    }

    public static Logger logger() {
        return LOGGER;
    }
}
