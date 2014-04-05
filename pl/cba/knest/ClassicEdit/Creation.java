package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Creation extends BukkitRunnable{
	protected String nick;
	
	
	public Creation(String nick) {
		this.nick = nick.toLowerCase();
	}
	
	public String getPlayerName(){
		return nick;
	}
	
	public void msgPlayer(String msg){
		Player p = Bukkit.getPlayer(nick);
		if(p!=null) p.sendMessage(msg);
	}
	
	public abstract String getName();
	public abstract boolean start();
	public abstract int getTaskid();
	public abstract void setTaskid(int taskid);
	
	public abstract void onBlockPhysics(BlockPhysicsEvent e);
}
