package pl.cba.knest.ClassicEdit;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Creation extends BukkitRunnable{
	public abstract String getName();
	public abstract String getPlayerName();
	public abstract void start();
}
