package com.mikitellurium.telluriumforge;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriumForge implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("telluriumforge");

	@Override
	public void onInitialize() {

	}

	public static Logger logger() {
		return LOGGER;
	}

}