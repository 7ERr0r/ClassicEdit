package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.Material;

import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;

public abstract class FilledCreation extends Creation{
	Filling f = new Filling(Material.AIR, (byte) 0);
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
