package com.github.benjibobsmc.BenjiCrateRewritten;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {

		log.info("BenjiCrate v" + this.getDescription().getVersion() + " has been enabled.");
		
	}
	
	@Override
	public void onDisable() {

		log.info("BenjiCrate v" + this.getDescription().getVersion() + " has been disabled.");
		
	}
	
}
