package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neatmonster.nocheatplus.NoCheatPlus;

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
		excs.put(getCommand("pause"), new PauseExecutor());
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

	public static synchronized void callEventWithoutNCP(Event event) {
		synchronized (Bukkit.getPluginManager()) {
			HandlerList handlers = event.getHandlers();
			RegisteredListener[] listeners = handlers.getRegisteredListeners();

			for(RegisteredListener registration : listeners) {
				try {
					if(registration.getPlugin() instanceof NoCheatPlus){
						continue;
					}
				} catch (Error e) {
					e.printStackTrace();
					continue;
				}

				if (registration.getPlugin().isEnabled()) {
					try {
						registration.callEvent(event);
					} catch (AuthorNagException ex) {
						Plugin plugin = registration.getPlugin();

						if (plugin.isNaggable()) {
							plugin.setNaggable(false);

							String author = "<NoAuthorGiven>";

							if (plugin.getDescription().getAuthors().size() > 0) {
								author = (String) plugin.getDescription()
										.getAuthors().get(0);
							}
							Bukkit.getServer().getLogger().log(Level.SEVERE,String.format(
													"Nag author: '%s' of '%s' about the following: %s",
													new Object[] {
															author,
															plugin.getDescription().getName(),
															ex.getMessage() }));
						}

					} catch (Throwable ex) {
						Bukkit.getServer().getLogger().log(Level.SEVERE,"Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getName(), ex);
					}
				}
			}
		}
	}
}
