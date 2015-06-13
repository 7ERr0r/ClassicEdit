package pl.cba.knest.ClassicEdit.selector;



import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.creation.ICreation;

public class AreaSelector extends Selector {
	public ICreation creation;
	public Location l1;
	public Location l2;
	public Location getLocationA() {
		return l1;
	}


	public Location getLocationB() {
		return l2;
	}


	public Player getPlayer(){
		return creation.getPlayer();
	}



	public ICreation getCreation(){
		return creation;
	}



	@Override
	public boolean handleInteract(PlayerInteractEvent e) {
		return false;
	}


	@Override
	public boolean start(ICreation c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}


}
