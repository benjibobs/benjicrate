package com.github.benjibobsmc.BenjiCrateRewritten;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

public class Main extends JavaPlugin{

	Logger log = Logger.getLogger("Minecraft");
	public static boolean fopen = false;
	public final MainListener lis = new MainListener(this, getConfig());
	
	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(lis, this);
		
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
			}else if(args.length != 0){
			
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
			
			}else{
				
				DoCommandHelp();
				
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
		}else if(new File(getDataFolder() + "/data", args[1] + ".yml").exists()){
			if(fopen == true){
		
			fopen = false;
			Inventory inv = getServer().createInventory(upi, 3*9, "Currently editing '" + args[1] + "'");
			upi.openInventory(inv);
		}else{
			
			Inventory inv = getServer().createInventory(upi, 3*9, "Currently editing '" + args[1] + "'");
			inv.setContents(loadInventory(args[1]));
			upi.openInventory(inv);
			
		}
		}else{
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Crate does not exist.");
		}
	}
	
	public void doCrateRemove(Player upi, String[] args){
		if(args.length !=2){
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Insufficent arguements!");
		}else if(new File(getDataFolder() + "/data", args[1] + ".yml").exists()){
			
			removeInventory(args[1]);
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] The crate '" + args[1] + "' has been removed successfully!");
			
		}else{
			upi.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] Crate does not exist.");
		}
	}
	
	public ItemStack[] loadInventory(String name)
    {
    	File folder = new File(this.getDataFolder(), "data");
    	if(!folder.exists())
    	{
    		folder.mkdirs();
    		return null;
    	}

    	String fileName = name + ".yml";
    	File file = new File(folder, fileName);
    	
    	if(!file.exists())
    		return null;
    	
    	FileInputStream fis = null;
    	Yaml yaml = new Yaml(new CustomClassLoaderConstructor(getClass().getClassLoader()));
    	ArrayList<SerializableItemStack> sItems = null;

		try 
		{
			fis = new FileInputStream(file);
			sItems = (ArrayList<SerializableItemStack>)yaml.load(fis);
			fis.close();
		} 
		catch (Exception e) 
		{
			if(fis != null)
			{
				try {
					fis.close();
				} catch (IOException e1) {
					this.getLogger().log(Level.WARNING, e.getMessage());
				}
			}
			this.getLogger().log(Level.WARNING, e.getMessage());
		}
    
		if(sItems != null)
		{
			ItemStack[] retval = new ItemStack[sItems.size()];
			for(int i=0; i<sItems.size(); i++)
			{
				if(sItems.get(i) == null)
					retval[i] = null;
				else
					retval[i] = sItems.get(i).getItemStack();
			}
			return retval;
		}
		
    	return null;
    }
	
	public void removeInventory(String name)
    {
    	File folder = new File(this.getDataFolder(), "data");
    	if(!folder.exists())
    	{
    		folder.mkdirs();
    		return;
    	}

    	String fileName = name + ".yml";
    	File file = new File(folder, fileName);
    	
    	if(!file.exists())
    		return;

    	file.delete();
    	
    }
	
}
