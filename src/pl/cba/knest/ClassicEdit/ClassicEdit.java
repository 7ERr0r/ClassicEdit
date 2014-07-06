package pl.cba.knest.ClassicEdit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import pl.cba.knest.ClassicEdit.Executors.BlockExecutor;
import pl.cba.knest.ClassicEdit.Executors.CuboidExecutor;
import pl.cba.knest.ClassicEdit.Executors.Executor;
import pl.cba.knest.ClassicEdit.Executors.GunExecutor;
import pl.cba.knest.ClassicEdit.Executors.LineExecutor;
import pl.cba.knest.ClassicEdit.Executors.MazeExecutor;
import pl.cba.knest.ClassicEdit.Executors.PauseExecutor;
import pl.cba.knest.ClassicEdit.Executors.SpheroidExecutor;


import fr.neatmonster.nocheatplus.NoCheatPlus;

public class ClassicEdit extends JavaPlugin{
	public static ClassicEdit plugin;
	public static int pertick;
	public static int droppertick;
	
	private Manager cm;
	
	

	public ClassicEdit(){
		plugin = this;
	}
	
	public void onEnable(){
		pertick = 256;
		droppertick = 1;
		Bukkit.getPluginManager().registerEvents(new ClickListener(), this);

		cm = new Manager();
		cm.runTaskTimer(this, 10L, 1L);
		
		
		try {
			Metrics m = new Metrics(this);
			m.start();
		} catch (IOException e){
			
		}
		
		
		
		log("Enabled !");
		
	}
	public void onDisable(){
		
	}
	
	
	public static long getLimit(Player p, boolean dropmode){
		if(p.hasPermission("ClassicEdit.limit.more")){
			return dropmode?2000000000:1000000;
		}else{
			return dropmode?2000000:20000;
		}
		
	}
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		ArrayList<String> params = new ArrayList<String>();
		String flags = "";
		params.add(label);
		for(String arg : args){
			if(arg.startsWith("-")){
				char[] chars = arg.toCharArray();
				for(int i = 1; i<chars.length; i++){
					flags += chars[i];
				}
			}else{
				params.add(arg);
			}
		}
		try{
			try{
				execute(s, params, flags);
			} catch (ExecutorException e) {
				e.send(s);
			}
		}catch(Exception e){
			e.printStackTrace();
			s.sendMessage(ChatColor.RED+"[ClassicEdit] An internal error occurred while attempting to perform this command");
		}

		return true;
	}
	private void execute(CommandSender s, List<String> params, String flags) throws ExecutorException {
		Command cmd = getCommand(params.remove(0));
		String name = cmd.getName();
		Executor e = null;
		if(name.equals("cuboid")){
			e = new CuboidExecutor();
		}else if(name.equals("spheroid")){
			e = new SpheroidExecutor();
		}else if(name.equals("line")){
			e = new LineExecutor();
		}else if(name.equals("maze")){
			e = new MazeExecutor();
		}else if(name.equals("pause")){
			e = new PauseExecutor();
		}else if(name.equals("gun")){
			e = new GunExecutor();
		}else if(name.equals("block")){
			e = new BlockExecutor();
		}
		if(e == null) throw new ExecutorException(ChatColor.RED+"Bad command");
		e.init(s, params, flags);
		e.execute();
		
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
					//e.printStackTrace();
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
