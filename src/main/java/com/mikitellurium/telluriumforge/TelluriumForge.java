package com.mikitellurium.telluriumforge;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriumForge implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("telluriumforge");
	private static final String MOD_ID = "telluriumforge";

	@Override
	public void onInitialize() {

	}

	public static Logger logger() {
		return LOGGER;
	}

	public static String modId() {
		return MOD_ID;
	}

}