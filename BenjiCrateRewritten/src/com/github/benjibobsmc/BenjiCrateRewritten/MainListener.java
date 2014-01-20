package com.github.benjibobsmc.BenjiCrateRewritten;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_7_R1.EntitySpider;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

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
			  player.sendMessage(ChatColor.GOLD + "[BenjiCrate] The crate '" + event.getInventory().getName().replace("Currently ", "").replace("editing '", "").replace("'", "") + "' has been edited successfully");
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
		  
		  
		  if(event.getEntity().getKiller() != null){
		  
	    for (Iterator i$ = this.config.getConfigurationSection("crates").getKeys(false).iterator(); i$.hasNext(); ) { 
	    	String cratename = (String)i$.next();
	    	ItemStack drop = null;
	    	ItemMeta meta = null;
	      

	    		if(config.getBoolean("crates." + cratename + ".drop") == true){
	            if (this.rand.nextInt(100) < this.config.getInt("crates." + cratename +".drop-chance"))
	            	
	            	
	            		
	            		
	            		
	            		drop = new ItemStack(config.getInt("crates." + cratename + ".id"), 1, (short)config.getInt("crates." + cratename + ".data"));
	            		meta = drop.getItemMeta();
	            		
	            		meta.setDisplayName(config.getString("crates." + cratename + ".name").replace("&1", ChatColor.GOLD + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "));
	            		
	            		if(config.getBoolean("crates." + cratename + ".has-custom-lore") == true){
	            		List<String> lore = new ArrayList<String>();
	            		lore.add(config.getString("crates." + cratename + ".lore").replace("&1", ChatColor.GOLD + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "));
	            		meta.setLore(lore);	
	            		}
	            		if(meta.getLore() != null){
	            		List<String> lore = meta.getLore();
	            		
	            		lore.add(ChatColor.GOLD + "Crate");
	            		
	            		meta.setLore(lore);
	            		}else{
	            		List<String> lore = new ArrayList<String>();
		            		
		            	lore.add(ChatColor.GOLD + "Crate");
		            		
		            	meta.setLore(lore);
	            		}
	            		drop.setItemMeta(meta);
	            
	            
	            		if(event.getEntity().getMetadata("isSpawnNatural").get(0).asBoolean() == true){
	            		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), drop);
	            		
	            		}
	            	
	    		}
	            			
	          }
	        
	       
	   
	    int chanceint;
		  }
	  }
	  
	  @EventHandler
	  public void onClick(PlayerInteractEvent event){
		  
		  for (Iterator i$ = this.config.getConfigurationSection("crates").getKeys(false).iterator(); i$.hasNext(); ) { 
		    	String cratename = (String)i$.next();
		      
		    	if(event.getItem() != null && event.getItem().getTypeId() == config.getInt("crates." + cratename + ".id") && event.getItem().getDurability() == config.getInt("crates." + cratename + ".data")){
		    		if(event.getItem().getItemMeta().getDisplayName().equals(config.getString("crates." + cratename + ".name").replace("&1", ChatColor.GOLD + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "))){
		    			if(!event.getPlayer().hasPermission("bcrate.use." + cratename.toLowerCase())){
		    				event.getPlayer().sendMessage(ChatColor.GOLD + "[BenjiCrate] You do not have permission to use this crate!");
		    			}else{
		    				if(config.getBoolean("crates." + cratename + ".bind") == true){
		    					if(event.getItem().getItemMeta().getLore().contains(ChatColor.RED + "" + event.getPlayer().getName())){
		    						event.setCancelled(true);
		    						Inventory inv = plugin.getServer().createInventory(event.getPlayer(), 3*9, cratename);
		    						inv.setContents(loadInventory(cratename));
		    						event.getPlayer().openInventory(inv);
		    						event.getPlayer().getInventory().remove(event.getItem());
		    					}
		    				}else if(event.getItem().getItemMeta().getLore().contains(ChatColor.GOLD + "Crate")){
		    				event.setCancelled(true);
		    				Inventory inv = plugin.getServer().createInventory(event.getPlayer(), 3*9, cratename);
    						inv.setContents(loadInventory(cratename));
    						event.getPlayer().openInventory(inv);
    						event.getPlayer().getInventory().remove(event.getItem());
		    			}
		    		}
		    		}
		    	}
		            		
		            		
		            		
		            
		    	
		    	
		    		
		            			
		          }
		  
	  }
	  
	  
	  @SuppressWarnings("unchecked")
		public ItemStack[] loadInventory(String name)
	    {
	    	File folder = new File(plugin.getDataFolder(), "data");
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
						plugin.getLogger().log(Level.WARNING, e.getMessage());
					}
				}
				plugin.getLogger().log(Level.WARNING, e.getMessage());
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
	  
	  @EventHandler
	  public void bindCrate(PlayerPickupItemEvent event){
		  
		  for (Iterator i$ = this.config.getConfigurationSection("crates").getKeys(false).iterator(); i$.hasNext(); ) { 
		    	String cratename = (String)i$.next();
		      
		    	if(event.getItem().getItemStack().getTypeId() == config.getInt("crates." + cratename + ".id") && event.getItem().getItemStack().getDurability() == config.getInt("crates." + cratename + ".data")){
		    		if(event.getItem().getItemStack().getItemMeta().getDisplayName().equals(config.getString("crates." + cratename + ".name").replace("&1", ChatColor.GOLD + "").replace("&2", ChatColor.DARK_GREEN + "").replace("&3", ChatColor.DARK_AQUA + "").replace("&4", ChatColor.DARK_RED + "").replace("&5", ChatColor.DARK_PURPLE + "").replace("&6", ChatColor.GOLD + "").replace("&7", ChatColor.GRAY + "").replace("&8", ChatColor.DARK_GRAY + "").replace("&9", ChatColor.BLUE + "").replace("&0", ChatColor.BLACK + "").replace("&c", ChatColor.RED + "").replace("&e", ChatColor.YELLOW + "").replace("&a", ChatColor.GREEN + "").replace("&b", ChatColor.AQUA + "").replace("&d", ChatColor.LIGHT_PURPLE + "").replace("&f", ChatColor.WHITE + "").replace("&l", ChatColor.BOLD + "").replace("&r", ChatColor.RESET + "").replace("&k", ChatColor.MAGIC + "").replace("&n", ChatColor.UNDERLINE + "").replace("&o", ChatColor.ITALIC + "").replace("&m", ChatColor.STRIKETHROUGH + "").replace("_", " "))){
		    			
		    				
		    				if(event.getItem().getItemStack().getItemMeta().getLore() != null && event.getItem().getItemStack().getItemMeta().getLore().contains(ChatColor.GOLD + "Crate")){
		    					
		    					if(config.getBoolean("crates." + cratename + ".bind") == true){
			    					if(event.getItem().getItemStack().getItemMeta().getLore().contains(ChatColor.RED + "" + event.getPlayer().getName())){

			    					}else{
			    						List<String> lore = event.getItem().getItemStack().getItemMeta().getLore();
			    						ItemMeta meta = event.getItem().getItemStack().getItemMeta();
			    						lore.add(ChatColor.RED + "" + event.getPlayer().getName());
			    						meta.setLore(lore);
			    						event.getItem().getItemStack().setItemMeta(meta);
			    					}
		    					
		    				}
		    					}
		    			}
		    		}
		    	}
		            		
		            		
		            		
		            
		    	
		    	
		    		
		            			
		          }
	  		
	  
	  
	  		@EventHandler
	  		public void metaAdd(CreatureSpawnEvent event){
	  			
	  			if(event.getSpawnReason().equals(SpawnReason.NATURAL)){
	  				event.getEntity().setMetadata("isSpawnNatural", new FixedMetadataValue(plugin.getPlugin(Main.class), true));
	  			}else{
	  				event.getEntity().setMetadata("isSpawnNatural", new FixedMetadataValue(plugin.getPlugin(Main.class), false));
	  			}
	  			
	  			
	  		}
	
		  
	  }
	  

