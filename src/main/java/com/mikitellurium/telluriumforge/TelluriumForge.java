package com.mikitellurium.telluriumforge;

import com.mikitellurium.telluriumforge.registry.SimpleRegistrationHelper;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriumForge implements ModInitializer {

	private static final String MOD_ID = "telluriumforge";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

}