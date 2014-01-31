package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ClassicEdit extends JavaPlugin{
	public static ClassicEdit plugin;
	public static int pertick;
	public static int droppertick;
	private Manager cm;
	
	private HashMap<PluginCommand, CommandExecutor> excs;

	public ClassicEdit(){
		plugin = this;
	}
	
	public void onEnable(){
		
		pertick = 50;
		droppertick = 2;
		
		excs = new HashMap<PluginCommand, CommandExecutor>();
		excs.put(getCommand("cuboid"), new CuboidExecutor());
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);
		for(Entry<PluginCommand, CommandExecutor> e : excs.entrySet()){
			e.getKey().setExecutor(e.getValue());
		}
		cm = new Manager();
		log("Enabled !");
		
	}
	public void onDisable(){
		
	}
	
	public Manager getManager(){
		return cm;
	}
	public static Manager getCuboidManager(){
		return plugin.getManager();
	}
	public void log(String str){
		getLogger().info(str);
	}
	
}
