package com.github.benjibobsmc.BenjiCrateRewritten;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

public class MainListener implements Listener{

	public static Main plugin;

	  public MainListener(Main instance)
	  {
	    plugin = instance;
	  }
	
	  
	  @EventHandler
	  public void invSaveHandling(InventoryCloseEvent event){
		  
		  if(event.getInventory().getName().indexOf("Currently editing '") != -1){
			  ItemStack[] items = event.getInventory().getContents();
			  saveInventory(items, event.getInventory().getName().replace("Currently ", "").replace("editing '", "").replace("'", ""));
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
	  
}