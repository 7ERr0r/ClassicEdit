package pl.cba.knest.ClassicEdit;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;

public class Session implements Runnable {
	private final UUID uuid;
	private Player player;
	private Queue<Creation> queue;
	private Creation pending;
	private boolean paused;
	
	
	public Session(UUID uuid){
		this.uuid = uuid;
		queue = new LinkedList<Creation>();
	}
	
	public void callPhysicsEvent(BlockPhysicsEvent e){
		Creation c = getActive();
		if(c != null){
			c.onBlockPhysics(e);
		}	
	}
	public void selectBlock(Block b){
		if(pending != null){
			for(Selector s : pending.getSelectors()){
				if(s.selectBlock(b)) return;
			}
		}
	}
	public void selectAir(Player p, Action a) {
		if(pending != null){
			for(Selector s : pending.getSelectors()){
				if(s.selectAir(p, a)) return;
			}
		}
	}
	@Override
	public void run() {
		Creation c = getActive();
		if(c != null){
			if(!isPaused()) c.run();
		}
	}

	public Creation getActive() {
		return queue.peek();
	}
	public Creation getPending(){
		return pending;
	}
	public Player getPlayer() {
		if(player == null || !player.isOnline()){
			player = Bukkit.getPlayer(uuid);
		}
		return player;
	}
	private void msgPlayer(String msg) {
		Player p = getPlayer();
		if(p != null){
			p.sendMessage(msg);
		}
		
	}
	public void addCreation(Creation c) {
		queue.add(c);
	}

	public void removeCreation(Creation c) {
		queue.remove(c);
	}
	public boolean isPaused(){
		return paused;
	}
	
	
	private void onPause(){
		msgPlayer("Paused");
	}
	private void onUnpause(){
		msgPlayer("Unpaused");
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
	public void togglePause() {
		setPaused(!paused);
		
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setPending(Creation c) {
		pending = c;	
	}

	public void stop() {
		queue.clear();
		pending = null;
	}


}
