package com.github.benjibobsmc.BenjiCrateRewritten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

public class ConfigUtil {

	private Main Plugin;	
	private HashMap<String,Object> ConfigMap = new HashMap<String, Object>(); 

	public Boolean ShowErrorsInClient = false;
	public Boolean AllowNonOpAccess = false;
	public Boolean VerboseStartup = false;
	public String SignTag = "Replicator";
	

	public ConfigUtil(Main plugin)
	{
		Plugin = plugin;
	}


	
    @SuppressWarnings("unchecked")
	public ItemStack[] loadInventory(String name)
    {
    	File folder = new File(Plugin.getDataFolder(), "data");
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
					Plugin.getLogger().log(Level.WARNING, e.getMessage());
				}
			}
			Plugin.getLogger().log(Level.WARNING, e.getMessage());
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
    
    public void saveInventory(ItemStack[] items, String name)
    {
    	
    	File folder = new File(Plugin.getDataFolder(), "data");
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
			Plugin.getLogger().log(Level.WARNING, e.getMessage());
		}

    }
    
    public void removeInventory(String name)
    {
    	File folder = new File(Plugin.getDataFolder(), "data");
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