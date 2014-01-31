package pl.cba.knest.ClassicEdit;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Manager {
	private HashMap<Player, Selector> scs;
	public Manager(){
		this.scs = new HashMap<Player, Selector>();
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
}
