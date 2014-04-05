package pl.cba.knest.ClassicEdit;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Manager {
	private HashMap<Player, Selector> scs;
	private HashMap<String, Creation> creas;
	public Manager(){
		this.scs = new HashMap<Player, Selector>();
		this.creas = new HashMap<String, Creation>();
	}
	
	public void setSelector(Player p, Selector s){
		scs.put(p, s);
	}
	public Selector getSelector(Player p){
		return scs.get(p);
	}
	public boolean isSelecting(Player p){
		return scs.containsKey(p);
	}
	public void removeSelector(Player p){
		scs.remove(p);
	}
	public boolean runCreation(Player p, Creation c){

		if(c.start()){
			creas.put(p.getName().toLowerCase(), c);
			c.setTaskid(Bukkit.getScheduler().scheduleSyncRepeatingTask(ClassicEdit.plugin, c, 1L, 1L));
			return true;
		}
		return false;
	}
	public void pauseCreation(Creation c){
		Bukkit.getScheduler().cancelTask(c.getTaskid());
		c.setTaskid(0);
	}
	public void unpauseCreation(Creation c){
		c.setTaskid(Bukkit.getScheduler().scheduleSyncRepeatingTask(ClassicEdit.plugin, c, 1L, 1L));
	}
	public void removeCreation(Creation c){
		Bukkit.getScheduler().cancelTask(c.getTaskid());
		creas.remove(c.getPlayerName());
	}
	public boolean isCuboiding(Player p){
		return creas.containsKey(p.getName().toLowerCase());
	}
	public Creation getCreation(Player p){
		return creas.get(p.getName().toLowerCase());
	}
}
