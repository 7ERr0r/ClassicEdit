package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

import pl.cba.knest.ClassicEdit.creation.Creation;
import pl.cba.knest.ClassicEdit.creation.CuboidCreation;


public class Manager extends BukkitRunnable {
	
	private HashMap<UUID, Session> sessions;
	private HashMap<String, Session> globals;
	public Manager(){
		this.sessions = new HashMap<UUID, Session>();
		this.globals = new HashMap<String, Session>();
	}
	
	public Session getSession(Player p){
		return getSession(p.getUniqueId());
	}
	public Session getSession(UUID uuid){
		if(!hasSession(uuid)){
			sessions.put(uuid, new Session(uuid));
		}
		return sessions.get(uuid);
	}
	public boolean hasSession(UUID uuid){
		return sessions.get(uuid) != null;		
	}
	public boolean hasSession(Player p){
		return hasSession(p.getUniqueId());		
	}

	@Override
	public void run() {
		Iterator<Session> i = sessions.values().iterator();
		while(i.hasNext()){
			Session s = i.next();
			/*if((s.getPlayer() == null && !s.isPaused()) || !s.getPlayer().isOnline()){
				if(s.getCreations().isEmpty()){
					i.remove();
					continue;
				}
			}*/
			s.run();
		}
	}
	public void callPhysicsEvent(BlockEvent e) {
		for(Session s : sessions.values()){
			s.callPhysicsEvent(e);
		}
		
	}

	public Creation getCreation(Player p) {
		return getSession(p).getActive();
	}

	public void append(CuboidCreation c) {
		c.getSession();
	}
	
	public Session getGlobal(String name){
		if(!globals.containsKey(name)){
			Session s = new Session(UUID.randomUUID());
			globals.put(name, s);
			sessions.put(s.getUUID(), s);
			return s;
		}
		return globals.get(name);
	}

	public HashMap<UUID, Session> getSessions() {
		return sessions;
	}
	
}
