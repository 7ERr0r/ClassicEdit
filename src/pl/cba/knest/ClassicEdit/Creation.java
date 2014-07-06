package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;

public abstract class Creation implements Runnable {
	protected String nick;
	private boolean pause;
	
	public Creation(String nick) {
		this.nick = nick.toLowerCase();
	}
	
	public String getPlayerName(){
		return nick;
	}
	
	public void msgPlayer(String msg){
		Player p = Bukkit.getPlayer(nick);
		if(p!=null) p.sendMessage(ChatColor.GOLD+"CE: "+msg);
	}

	public abstract String getName();
	public String getFullName(){
		return getName();
	}
	public void start(){
		ClassicEdit.getCuboidManager().addCreation(nick, this);
		init();
	}
	public abstract void init();
	public void stop(){
		
		ClassicEdit.getCuboidManager().removeCreation(this);
	}

	public void pause(){
		pause = true;
	}
	public void unpause(){
		pause = false;
	}
	public void togglePause(){
		pause = !pause;
	}
	
	public boolean isPause(){
		return pause;
	}
	
	public void msgStart(){
		msgPlayer(ChatColor.YELLOW+"Creating "+getName());
	}
	
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopped "+getName());
	}
	
	public void onBlockPhysics(BlockPhysicsEvent e){}
}
