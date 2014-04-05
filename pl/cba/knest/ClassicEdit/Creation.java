package pl.cba.knest.ClassicEdit;

import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Creation extends BukkitRunnable{
	public abstract String getName();
	public abstract String getPlayerName();
	public abstract boolean start();
	public abstract int getTaskid();
	public abstract void setTaskid(int taskid);
	
	public abstract void onBlockPhysics(BlockPhysicsEvent e);
}
