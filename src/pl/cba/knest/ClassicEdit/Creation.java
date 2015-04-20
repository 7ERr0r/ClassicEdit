package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;

public abstract class Creation implements Runnable {
	protected Session session;
	boolean started;
	
	public Creation(Session session) {
		this.session = session;
	}
	
	public abstract String getName();
	public abstract void onBlockPhysics(BlockPhysicsEvent e);
	public abstract void init();
	public abstract Selector[] getSelectors();
	
	public Session getSession(){
		return session;
	}
	public Player getPlayer(){
		return session.getPlayer();
	}
	
	public void msgPlayer(String msg){
		Player p = getPlayer();
		if(p!=null) p.sendMessage(ChatColor.GOLD+"CE: "+msg);
	}


	public String getFullName(){
		return getName();
	}
	public void start(){
		if(!started){
			started = true;
			session.addCreation(this);
			session.setPending(null);
			init();
			msgStart();
		}
	}

	public void stop(){
		session.removeCreation(this);
		msgEnd();
	}

	public void pause(){
		session.pause();
	}
	public void unpause(){
		session.unpause();
	}
	public void togglePause(){
		session.togglePause();
	}
	public boolean isPaused() {
		return session.isPaused();
	}
	
	public void msgStart(){
		msgPlayer(ChatColor.YELLOW+"Creating "+getFullName());
	}
	
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopped "+getFullName());
	}
	public void attach(){
		session.setPending(this);
		start();
	}


	


}
