package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SimpleCreation extends Creation{
	String nick;
	Location l1;
	Location l2;
	World w;
	Filling f;
	
	int taskid;
	
	public SimpleCreation(String nick){
		this.nick = nick;
	}
	@Override
	public void run() {
		Bukkit.getScheduler().cancelTask(taskid);
	}

	@Override
	public String getName() {
		return "2-point structure";
	}

	@Override
	public String getPlayerName() {
		return nick;
	}


	public void setPoints(Location l1, Location l2){
		this.l1 = l1;
		this.w = l1.getWorld();
		this.l2 = l2;
	}
	public void setFilling(Filling f){
		this.f = f;
	}
	void shedule(){
		taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(ClassicEdit.plugin, this, 1L, 1L);
	}
	@Override
	public void start(){
		//shedule();
	}
	public Filling getFilling(){
		return f;
	}
}
