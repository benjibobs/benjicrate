package com.github.benjibobsmc.BenjiCrateRewritten;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.Yaml;

public class MainListener implements Listener{

	  public static Main plugin;
	  public FileConfiguration config = null;
	  public Random rand = new Random();

	  public MainListener(Main instance, FileConfiguration conf)
	  {
		config = conf;
	    plugin = instance;
	  }
	
	  
	  @EventHandler
	  public void invSaveHandling(InventoryCloseEvent event){
		  
		  if(event.getInventory().getName().indexOf("Currently editing '") != -1){
			  ItemStack[] items = event.getInventory().getContents();
			  saveInventory(items, event.getInventory().getName().replace("Currently ", "").replace("editing '", "").replace("'", ""));
			  Player player = (Player)event.getPlayer();
			  player.sendMessage(ChatColor.DARK_BLUE + "[BenjiCrate] The crate '" + event.getInventory().getName().replace("Currently ", "").replace("editing '", "").replace("'", "") + "' has been edited successfully");
		  }
		  
	  }
	  
	  public void saveInventory(ItemStack[] items, String name)
	    {
	    	
	    	File folder = new File(plugin.getDataFolder(), "data");
	    	String fileName = name + ".yml";
	    	File file = new File(folder, fileName);
	    	Yaml yaml = new Yaml();

	    	// create serializableitemstack array from items
	    	SerializableItemStack[] sItems = new SerializableItemStack[items.length];
	    	for(int i=0; i<items.length; i++)
	    		if(items[i] == null)
	    			sItems[i] = null;
	    		else
	    			sItems[i] = new SerializableItemStack((ItemStack)items[i]);
	    	
			try 
			{
				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter out = new OutputStreamWriter(fos);
				out.write(yaml.dump(sItems));
				out.close();
				fos.close();
			} 
			catch (Exception e) 
			{
				plugin.getLogger().log(Level.WARNING, e.getMessage());
			}

	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  @EventHandler(priority=EventPriority.HIGH)
	  public void onEntityDeath(EntityDeathEvent event) {
		  
		  
		  
		  
	    for (Iterator i$ = this.config.getConfigurationSection("crates").getKeys(false).iterator(); i$.hasNext(); ) { 
	    	String cratename = (String)i$.next();
	    	ItemStack drop = null;
	    	ItemMeta meta = null;
	      

	    		if(config.getBoolean("crates." + cratename + ".drop") == true){
	            if (this.rand.nextInt(100) < this.config.getInt("crates." + cratename +".drop-chance"))
	            	
	            	
	            		
	            		
	            		
	            		drop = new ItemStack(config.getInt("crates." + cratename + ".id"), 1, (short)config.getInt("crates." + cratename + ".data"));
	            		meta = drop.getItemMeta();
	            		if(config.getBoolean("crates." + cratename + ".has-custom-name") == true){
	            		meta.setDisplayName(config.getString("crates." + cratename + ".name").replace("&1", ChatColor.DARK_BLUE + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "));
	            		}
	            		if(config.getBoolean("crates." + cratename + ".has-custom-lore") == true){
	            		List<String> lore = new ArrayList<String>();
	            		lore.add(config.getString("crates." + cratename + ".lore").replace("&1", ChatColor.DARK_BLUE + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "));
	            		meta.setLore(lore);	
	            		}
	            		
	            		drop.setItemMeta(meta);
	            
	            
	            		
	            		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), drop);
	            		
	            	
	    		}
	            			
	          }
	        
	       
	   
	    int chanceint;
	  }
	  
	  
	  
}
