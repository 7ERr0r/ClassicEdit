package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

import pl.cba.knest.ClassicEdit.creation.CuboidCreation;


public class Manager extends BukkitRunnable {
	private HashMap<UUID, Session> sessions;
	public Manager(){
		this.sessions = new HashMap<UUID, Session>();
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
		for(Session s : sessions.values()){
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
}
