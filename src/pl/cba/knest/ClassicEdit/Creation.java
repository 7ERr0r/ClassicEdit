package pl.cba.knest.ClassicEdit;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;

public abstract class Creation implements Runnable {
	protected Session session;
	boolean started;
	protected boolean initialised;
	protected Queue<Selector> selectors;

	public Creation(){
		selectors = new LinkedList<Selector>();
	}
	public abstract String getName();
	public abstract void onBlockPhysics(BlockPhysicsEvent e);
	public abstract boolean init();
	public boolean isInitialised(){
		return initialised;
	}
	public void tick(){
		if(!initialised){
			if(init()){
				initialised = true;
			}else return;
		}
		run();
	}
	public Queue<Selector> getSelectors(){
		return selectors;
	}
	
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
			msgStart();
		}else{
			ClassicEdit.log("Warning: start() was invoked more than once");
			msgPlayer("Warning: start() was invoked more than once");
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
	
	public void handleBlockSelect(Block b){
		Selector s = selectors.peek();
		if(s != null && s.selectBlock(b)){
			nextSelector();
		}
	}
	public void handleAirSelect(Player p, Action a){
		Selector s = selectors.peek();
		if(s != null && s.selectAir(p, a)){
			nextSelector();
		}
	}
	public void nextSelector(){
		Selector s = selectors.poll();
		if(s != null){
			s.end();
			s = selectors.peek();
			if(s != null){
				s.start();
			}
		}
		
	}
	
	public void attach(Session session){
		this.session = session;
		session.setPending(this);
		start();
	}


	


}
