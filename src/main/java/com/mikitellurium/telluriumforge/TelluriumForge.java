package com.mikitellurium.telluriumforge;

import com.mikitellurium.telluriumforge.test.ExampleConfig;
import com.mikitellurium.telluriumforge.test.TestEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriumForge implements ModInitializer {

	private static final String MOD_ID = "telluriumforge";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ExampleConfig.buildConfig();
		TestEvents.registerEvents();
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

}