package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.Location;


public abstract class InfiniteCreation extends FilledCreation{

	public InfiniteCreation(String nick) {
		super(nick);
	}

	public abstract void click(Location location);
	public void init(){
		
	}


}
