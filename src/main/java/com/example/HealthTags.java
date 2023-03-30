package com.example;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = HealthTags.MODID,
	name = HealthTags.NAME,
	version = HealthTags.VERSION
)
public class HealthTags {
	public static final String MODID = "healthtags";
	public static final String NAME = "HealthTags";
	public static final String VERSION = "0.1";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent preinit) {
		LOGGER.info("Hello, world!");
	}
}
