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

		File folder = new File(getDataFolder(), "data");
	    if(new File(getDataFolder(), "data") == null || !new File(getDataFolder(), "data").exists()){
			
			folder.mkdirs();
			
		}
	    
	    File dummy = new File(folder, "PLACEHOLDER" + ".donotdelete");
		if(!dummy.exists()){
			try {
				dummy.createNewFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
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
	        case "edit":
		          doCrateEdit(upi, args);
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
		}else{
		
		if(new File(getDataFolder() + "/data", args[1] + ".yml").exists()){

		upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Crate already exists, please choose a different name.");	
			
		}else{      		
		
		File crate = new File(getDataFolder() + "/data", args[1] + ".yml");
		try {
			fopen = true;
			crate.createNewFile();
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] The crate '" + args[1] + "' has been created, please use /crate edit to edit it's contents.");
		} catch (IOException e) {
			e.printStackTrace();
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] CRATE CREATION FAILED, PLEASE CHECK CONSOLE FOR AN ERROR!");
		}
		
		}
		
		}
		
	}
	
	public void DoCommandHelp(){
		
		
		
	}
	
	public void doCrateEdit(Player upi, String[] args){
		if(args.length != 2){
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Insufficent arguements!");
		}else{
			if(fopen == true){
		
			fopen = false;
			Inventory inv = getServer().createInventory(upi, 3*9, "Currently editing '" + args[1] + "'");
			upi.openInventory(inv);
		}else{
			
		}
		}
	}
	
}
