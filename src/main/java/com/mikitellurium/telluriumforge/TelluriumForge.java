package com.mikitellurium.telluriumforge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TelluriumForge.MOD_ID)
public class TelluriumForge {

	public static final String MOD_ID = "telluriumforge";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public TelluriumForge(IEventBus modEventBus) {
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

}