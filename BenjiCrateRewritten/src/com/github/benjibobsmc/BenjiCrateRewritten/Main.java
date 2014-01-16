package com.github.benjibobsmc.BenjiCrateRewritten;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	Logger log = Logger.getLogger("Minecraft");
	public static boolean fopen = false;
	
	@Override
	public void onEnable() {

		log.info("BenjiCrate v" + this.getDescription().getVersion() + " has been enabled.");
		
	}
	
	@Override
	public void onDisable() {

		log.info("BenjiCrate v" + this.getDescription().getVersion() + " has been disabled.");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(label.equalsIgnoreCase("bcrate") && sender.hasPermission("bcrate.use")){
			
			if(!(sender instanceof Player)){
				sender.sendMessage("You must be a player to use this right now, sorry.");
			}else{
			
			Player upi = (Player)sender;
			
			switch (args[0].toLowerCase()) {
	        case "create":
	          doCrateCreate(upi, args);
	          break;
	        default:
	          DoCommandHelp();
	        }
			
			}
		}
		
		return false;
	}
	
	public void doCrateCreate(Player upi, String[] args){
		if(args.length != 2){
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Insufficent arguements!");
		}
		
		if(new File(getDataFolder() + "/data", args[1] + ".yml").exists()){

		}else{      		
		
		File crate = new File(getDataFolder() + "/data", args[1] + ".yml");
		try {
			crate.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
		
		
	}
	
	public void DoCommandHelp(){
		
		
		
	}
	
}
