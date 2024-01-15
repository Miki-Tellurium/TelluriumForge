package com.mikitellurium.telluriumforge;

import com.mikitellurium.telluriumforge.test.ExampleConfig;
import com.mikitellurium.telluriumforge.test.TestEvents;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.WorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelluriumForge implements ModInitializer {

	private static final String MOD_ID = "telluriumforge";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		TestEvents.registerEvents();
		ExampleConfig.buildConfig();
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}
	// todo networking
	// todo add info to mod json
}