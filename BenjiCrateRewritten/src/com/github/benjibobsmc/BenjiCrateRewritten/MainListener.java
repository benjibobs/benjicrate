package com.github.benjibobsmc.BenjiCrateRewritten;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
	    	String mob = (String)i$.next();
	    	EntityType mobt = EntityType.fromName(mob);
	      
	        if (this.config.getString("mobs." + mob + ".percent-mode").equals("Individual")) {
	          for (String drop : this.config.getConfigurationSection("mobs." + mob + ".drops").getKeys(false)) {
	            if (this.rand.nextInt(100) < this.config.getInt("mobs." + mob + ".drops." + drop))
	            	
	            	
	            	
	            	
	            	
	            		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), GetCrateItemStack(drop));
	            		
	            	
	            	
	            			
	          }
	        }
	        else if (this.config.getString("mobs." + mob + ".percent-mode").equals("XOR")) {
	          int chanceint = this.rand.nextInt(100);
	          for (String drop : this.config.getConfigurationSection("mobs." + mob + ".drops").getKeys(false))
	            if ((chanceint >= this.config.getInt("mobs." + mob + ".drops." + drop + ".lower")) && (chanceint < this.config.getInt("mobs." + mob + ".drops." + drop + ".upper"))) {
	              event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), GetCrateItemStack(drop));
	              break;
	            }
	        }
	    
	    }
	    String mob;
	    int chanceint;
	  }
	  
	  
	  
}
