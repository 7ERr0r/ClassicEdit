package pl.cba.knest.ClassicEdit.creation;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.Selector;

import com.mongodb.BasicDBObject;

public abstract class Creation implements ICreation, Runnable {
	protected Session session;
	protected boolean started;
	protected boolean initialised;
	protected Queue<Selector> selectors;

	public Creation(){
		selectors = new LinkedList<Selector>();
	}

	public abstract String getName();
	public abstract void onBlockPhysics(BlockEvent e);
	public abstract boolean init();
	public boolean isInitialised(){
		return initialised;
	}
	public void tick(){
		if(!initialised){
			if(init()){
				initialised = true;
				start();
			}
			return;
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
		msgEnd();
		session.removeCreation(this);
		if(session.getPending()==this) session.setPending(null);

	}
	public void rotate(){
		session.removeCreation(this);
		session.addCreation(this);
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
		boolean active = session.isActive(this);
		msgPlayer(ChatColor.YELLOW+(active?"Creating ":"Queued ")+getFullName());
	}
	
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopped "+getFullName());
	}
	
	public void handleInteract(PlayerInteractEvent e){
		Selector s = selectors.peek();
		if(s != null && s.handleInteract(e)){
			nextSelector();
		}
	}

	public void nextSelector(){
		Selector s = selectors.poll();
		if(s != null){
			s.end();
			startSelector();
		}
	}
	public void startSelector(){
		Selector s = selectors.peek();
		if(s != null){
			if(s.start(this)){
				nextSelector();
			}
		}
	}
	
	public void attach(Session session){
		this.session = session;
		session.setPending(this);
		startSelector();
	}
	public boolean isUseless(){
		return false;
	}
	
	public String toString(){
		return getName();
	}

	public static Creation deserializeCreation(BasicDBObject o) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Creation creation = null;
		String type = o.getString("type");
		Class<?> clazz = Class.forName(type);
		
		if(clazz.isAssignableFrom(Creation.class)){
			creation = (Creation) clazz.newInstance();
			creation.deserialize(o);
			
		}
		return creation;
	}
	public void deserialize(BasicDBObject o) {
		
	}
	public BasicDBObject serialize() {
		BasicDBObject o = new BasicDBObject();
		Class<?> clazz = this.getClass();
		o.append("type", clazz.getName());
		return o;
	}
	public static BasicDBObject serializeCreation(Creation creation){
		return creation.serialize();
	}
}
