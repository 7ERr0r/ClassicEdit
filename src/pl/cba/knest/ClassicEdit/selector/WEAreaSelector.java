package pl.cba.knest.ClassicEdit.selector;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.creation.AreaCreation;

public class WEAreaSelector extends AreaSelector {
	Location l1;
	Location l2;
	AreaCreation creation;
	@Override
	public Location getLocationMin() {
		return l1;
	}

	@Override
	public Location getLocationMax() {
		return l2;
	}

	@Override
	public void setCreation(AreaCreation c) {
		this.creation = c;

	}

	@Override
	public Player getPlayer() {
		return creation.getPlayer();
	}

	@Override
	public boolean handleInteract(PlayerInteractEvent e) {
		return false;
	}

	@Override
	public void start() {
		if(ClassicEdit.getWorldEdit() != null){
			msgPlayer(ChatColor.LIGHT_PURPLE+"Selected with WorldEdit");
		}else{
			msgPlayer(ChatColor.RED+"WorldEdit plugin not found");
			creation.stop();
		}
	}

	@Override
	public void end() {
		
	}

}
