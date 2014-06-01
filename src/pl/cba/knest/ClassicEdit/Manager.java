package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class Manager extends BukkitRunnable {
	private HashMap<String, Selector> scs;
	private HashMap<String, Queue<Creation>> creas;
	public Manager(){
		this.scs = new HashMap<String, Selector>();
		this.creas = new HashMap<String, Queue<Creation>>();
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
	
	private void put(String nick, Creation c){
		if(!creas.containsKey(nick.toLowerCase())){
			creas.put(nick.toLowerCase(), new LinkedList<Creation>());
		}
		Queue<Creation> q = creas.get(nick.toLowerCase());
		if(!q.contains(c)) q.add(c);
		
	}
	
	
	public void addCreation(String nick, Creation c){
		put(nick,c);
		if(getCreation(nick)==c){
			c.msgPlayer(ChatColor.YELLOW+"Started "+c.getName());
		}else{
			c.msgPlayer(ChatColor.YELLOW+"Queued "+c.getName());
		}
	}

	public void removeCreation(Creation c){
		Queue<Creation> q = creas.get(c.getPlayerName().toLowerCase());
		if(q.remove(c)){
			c.msgPlayer(ChatColor.YELLOW+"Removed "+c.getName());
		}
		Creation nc = q.peek();
		if(nc!=null){
			c.msgPlayer(ChatColor.YELLOW+"Started next "+nc.getName());
		}
		
	}
	public Creation getCreation(Player p){
		return getCreation(p.getName());
	}

	public Creation getCreation(String nick){
		if(!creas.containsKey(nick.toLowerCase())) return null;
		return creas.get(nick.toLowerCase()).peek();
	}
	@Override
	public void run() {
		for(Queue<Creation> q : creas.values()){
			Creation c = q.peek();
			if(c == null) continue;
			if(!c.isPause()) c.run();
		}
	}
	public void stopCreations(String nick) {
		Queue<Creation> q = creas.get(nick.toLowerCase());
		q.clear();
	}
}
