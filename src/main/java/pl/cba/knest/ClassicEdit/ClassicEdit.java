package pl.cba.knest.ClassicEdit;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.mongodb.MongoTimeoutException;

import pl.cba.knest.ClassicEdit.executor.BlockExecutor;
import pl.cba.knest.ClassicEdit.executor.ClassicEditExecutor;
import pl.cba.knest.ClassicEdit.executor.CuboidExecutor;
import pl.cba.knest.ClassicEdit.executor.Executor;
import pl.cba.knest.ClassicEdit.executor.GunExecutor;
import pl.cba.knest.ClassicEdit.executor.LineExecutor;
import pl.cba.knest.ClassicEdit.executor.MazeExecutor;
import pl.cba.knest.ClassicEdit.executor.PauseExecutor;
import pl.cba.knest.ClassicEdit.executor.PerspectiveExecutor;
import pl.cba.knest.ClassicEdit.executor.SpheroidExecutor;
import pl.cba.knest.ClassicEdit.listener.PhysicsListener;
import pl.cba.knest.ClassicEdit.listener.PlayerListener;
import fr.neatmonster.nocheatplus.NoCheatPlus;

public class ClassicEdit extends JavaPlugin{
	public static ClassicEdit plugin;
	public static int pertick;
	public static int droppertick;
	
	private Manager creationManager;
	
	private Backend creationBackend;

	public ClassicEdit(){
		plugin = this;
	}
	
	public void onEnable(){
		pertick = 6666;
		droppertick = 1;
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new PhysicsListener(), this);
		creationManager = new Manager();
		creationManager.runTaskTimer(this, 10L, 1L);
		
		try {
			Metrics m = new Metrics(this);
			m.start();
		} catch (IOException e){
			
		}
		saveConfig();
		log("Enabled !");
		
	}
	public void onDisable(){
		
	}
	
	
	public static long getLimit(Player p, boolean dropmode){
		if(p.hasPermission("ClassicEdit.more")){
			return dropmode?256:8192;
		}else{
			return dropmode?128:1024;
		}
		
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

		List<String> params = new ArrayList<String>(Arrays.asList(args));
		try{
			try{
				execute(s, params, cmd, label);
			} catch (ExecutorException e) {
				e.send(s);
			}
		}catch(Exception e){
			e.printStackTrace();
			s.sendMessage(ChatColor.RED+"[ClassicEdit] An internal error occurred while attempting to perform this command");
		}

		return true;
	}
	
	private void execute(CommandSender s, List<String> params, Command cmd, String label) throws ExecutorException {
		
		String name = cmd.getName();
		Executor e = null;
		if(name.equals("classicedit")){
			perms("ClassicEdit.main", s);
			e = new ClassicEditExecutor();
		}else if(name.equals("cuboid")){
			perms("ClassicEdit.create.cuboid", s);
			e = new CuboidExecutor();
		}else if(name.equals("spheroid")){
			perms("ClassicEdit.create.spheroid", s);
			e = new SpheroidExecutor();
		}else if(name.equals("persp")){
			perms("ClassicEdit.create.perspective", s);
			e = new PerspectiveExecutor();
		}else if(name.equals("line")){
			perms("ClassicEdit.create.line", s);
			e = new LineExecutor();
		}else if(name.equals("maze")){
			perms("ClassicEdit.create.maze", s);
			e = new MazeExecutor();
		}else if(name.equals("pause")){
			e = new PauseExecutor();
		}else if(name.equals("gun")){
			perms("ClassicEdit.fun.gun", s);
			e = new GunExecutor();
		}else if(name.equals("block")){
			e = new BlockExecutor();
		}
		if(e == null) throw new ExecutorException(ChatColor.RED+"Bad command");
		e.init(s, params, label);
		e.execute();
		
	}
	
	public boolean perms(String perm, CommandSender s) throws ExecutorException{
		if(!s.hasPermission(perm) && !s.isOp()){
			throw new ExecutorException(ChatColor.RED+"You do not have permission to do this");
		}
		return true;
	}
	
	public Manager getManager(){
		return creationManager;
	}
	
	public static Manager getCreationManager(){
		return plugin.getManager();
	}
	
	public static void log(String str){
		plugin.getLogger().info(str);
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

	public static ClassicEdit getInstance(){
		return plugin;
	}

	public static boolean isWorldEdit(){
		try{
			Class.forName("com.sk89q.worldedit.WorldEdit");
			return true;
		}catch(ClassNotFoundException e){
			return false;
		}
	}
	public void dialBackend(){
		try {
			creationBackend = Backend.create(getConfig().getString("db.host", "localhost"), getConfig().getInt("db.port", 27017), getConfig().getString("db.database", "classicedit"));
		} catch (UnknownHostException e) {
			log("Could not create MongoDB connection: "+e.getMessage());
		} catch (MongoTimeoutException e) {
			log("MongoDB connection timed out: "+e.getMessage());
		}
	}
	public Backend getBackend(){
		if(creationBackend == null){
			dialBackend();
		}
		return creationBackend;
	}
	
}
