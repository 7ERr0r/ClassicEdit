package pl.cba.knest.ClassicEdit;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.creation.Creation;

public class Session implements Runnable {
	private final UUID uuid;
	private Player player;
	private Queue<Creation> queue;
	private Creation pending;
	private boolean paused;
	private Clipboard clipboard;
	
	
	public Session(UUID uuid){
		this.uuid = uuid;
		queue = new LinkedList<Creation>();
	}
	
	public void callPhysicsEvent(BlockEvent e){
		Creation c = getActive();
		if(c != null){
			c.onBlockPhysics(e);
		}	
	}

	public void handleInteract(PlayerInteractEvent e) {
		if(getPending() != null){
			getPending().handleInteract(e);
		}
		if(getActive() != null){
			getActive().handleInteract(e);
		}
		
	}
	
	@Override
	public void run() {
		Creation c = getActive();
		if(c != null && !isPaused()){
			try{
				c.tick();
			}catch(Exception e){
				e.printStackTrace();
				pause();
				msgPlayer("Active creation: exception caught, paused");
			}
		}
		c = getPending();
		if(c != null && !c.isInitialised()){
			try{
				c.tick();
			}catch(Exception e){
				setPending(null);
				e.printStackTrace();
				msgPlayer("Pending creation: exception caught, removed");
			}
		}
	}

	public Creation getActive(){
		return queue.peek();
	}
	
	public boolean isActive(Creation c){
		return queue.peek() == c;
	}
	
	public Creation getPending(){
		return pending;
	}
	
	public Player getPlayer(){
		if(player == null || !player.isOnline()){
			player = Bukkit.getPlayer(uuid);
		}
		return player;
	}
	
	private void msgPlayer(String msg){
		Player p = getPlayer();
		if(p != null){
			p.sendMessage(msg);
		}
		
	}
	
	public void addCreation(Creation c){
		queue.add(c);
	}

	public void removeCreation(Creation c){
		queue.remove(c);
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	
	private void onPause(){
		//msgPlayer("Paused");
	}
	
	private void onUnpause(){
		//msgPlayer("Unpaused");
	}
	
	public void setPaused(boolean p){
		if(p == paused) return;
		paused = p;
		if(p){
			onPause();
		}else{
			onUnpause();
		}
		
	}
	
	public void pause(){
		setPaused(true);
	}
	
	public void unpause(){
		setPaused(false);
	}
	
	public void togglePause(){
		setPaused(!paused);
		
	}

	public UUID getUUID(){
		return uuid;
	}

	public void setPending(Creation c){
		pending = c;	
	}

	public void stop(){
		queue.clear();
		pending = null;
	}

	public Queue<Creation> getCreations(){
		return queue;
	}
	
	public void skipUseless(){
		Creation c = getActive();
		if(c != null && c.isUseless()){
			c.stop();
		}
		while(queue.peek() != null){
			c = queue.peek();
			if(c.isUseless()){
				queue.poll();
			}else{
				break;
			}
		}
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}



}
