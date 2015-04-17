package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;

public abstract class FilledCreation extends Creation{
	Filling f = new Filling(Material.AIR, (byte) 0);
	
	int placed = 0;
	long sum = 0;
	int ticksDone = 0;
	
	
	public String getFullName(){
		return getName()+" of "+getFilling();
	}
	public void msgStart(){
		msgPlayer(ChatColor.YELLOW+"Creating "+getFullName());
	}
	
	public void msgEnd(){
			msgPlayer(ChatColor.YELLOW+"Created "+sum+" block"+(sum==1?"":"s")+" of "+getFilling().toString());
	}
	public FilledCreation(String nick) {
		super(nick);
	}
	public void setFilling(Filling f){
		this.f = f;
	}
	public Filling getFilling(){
		return f;
	}
}
