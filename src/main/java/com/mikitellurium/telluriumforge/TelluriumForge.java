package com.mikitellurium.telluriumforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod("telluriumforge")
public class TelluriumForge {
	private static final String MOD_ID = "telluriumforge";
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