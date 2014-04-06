package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Creation extends BukkitRunnable{
	protected String nick;
	int taskid;
	
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
	public int getTaskid(){
		return taskid;
	}
	public void setTaskid(int taskid){
		this.taskid = taskid;
	}

	public abstract String getName();
	public abstract boolean start();
	public void stop(){
		ClassicEdit.getCuboidManager().removeCreation(this);
	}
	public void pause(){
		ClassicEdit.getCuboidManager().pauseCreation(this);
	}
	public void onBlockPhysics(BlockPhysicsEvent e){}
}
