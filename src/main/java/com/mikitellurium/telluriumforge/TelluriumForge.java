package com.mikitellurium.telluriumforge;

import com.mikitellurium.telluriumforge.test.NetworkingTest;
import com.mikitellurium.telluriumforge.test.RegistryTest;
import com.mikitellurium.telluriumforge.test.TestEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TelluriumForge.MOD_ID)
public class TelluriumForge {

	public static final String MOD_ID = "telluriumforge";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public TelluriumForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		RegistryTest.register(modEventBus);
		TestEvents.registerEvents(modEventBus);
		NetworkingTest.register();
	}

	public static String modId() {
		return MOD_ID;
	}

	public static Logger logger() {
		return LOGGER;
	}

}