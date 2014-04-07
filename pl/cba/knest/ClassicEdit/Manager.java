package pl.cba.knest.ClassicEdit;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Manager {
	private HashMap<String, Selector> scs;
	private HashMap<String, Creation> creas;
	public Manager(){
		this.scs = new HashMap<String, Selector>();
		this.creas = new HashMap<String, Creation>();
	}
	public void setSelector(Player p, Selector s){
		setSelector(p.getName().toLowerCase(), s);
	}
	public void setSelector(String nick, Selector s){
		removeSelector(nick);
		scs.put(nick, s);
	}
	public Selector getSelector(Player p){
		return getSelector(p.getName().toLowerCase());
	}
	public Selector getSelector(String nick){
		return scs.get(nick);
	}
	public boolean isSelecting(String nick){
		return scs.containsKey(nick);
	}
	public boolean isSelecting(Player p){
		return isSelecting(p.getName().toLowerCase());
	}
	public void removeSelector(Player p){
		removeSelector(p.getName().toLowerCase());
	}
	public void removeSelector(String nick){
		if(isSelecting(nick)){
			scs.get(nick).end();
			scs.remove(nick);
		}
	}
	public void runCreation(String nick, Creation c){
		creas.put(nick, c);
		run(c);
	}
	public void run(Creation c){
		c.setTaskid(Bukkit.getScheduler().scheduleSyncRepeatingTask(ClassicEdit.plugin, c, 1L, 1L));
	}
	public void pauseCreation(Creation c){
		Bukkit.getScheduler().cancelTask(c.getTaskid());
		c.setTaskid(0);
	}
	public void unpauseCreation(Creation c){
		run(c);
	}
	public void removeCreation(Creation c){
		Bukkit.getScheduler().cancelTask(c.getTaskid());
		creas.remove(c.getPlayerName());
	}
	public boolean isRunning(Player p){
		return isRunning(p.getName().toLowerCase());
	}
	public boolean isRunning(String nick){
		return creas.containsKey(nick);
	}
	public Creation getCreation(Player p){
		return creas.get(p.getName().toLowerCase());
	}
}
