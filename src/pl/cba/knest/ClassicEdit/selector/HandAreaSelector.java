package pl.cba.knest.ClassicEdit.selector;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.creation.ICreation;

public class HandAreaSelector extends AreaSelector {
	
	private ICreation creation;
	private int stage = 0;
	private Location l1;
	private Location l2;


	public Player getPlayer(){
		return creation.getPlayer();
	}



	public ICreation getCreation(){
		return creation;
	}



	@Override
	public boolean handleInteract(PlayerInteractEvent e){
		if(e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
		Block b = e.getClickedBlock();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			b = b.getRelative(e.getBlockFace());
		}
		if(stage == 0){
			l1 = b.getLocation();
			e.setCancelled(true);
		}else if(stage == 1){
			l2 = b.getLocation();
			setFillingAuto();
			e.setCancelled(true);
		}else{
			// done
		}
		stage++;
		return stage > 1;
	}
	public boolean start(ICreation c){
		this.creation = c;
		msgPlayer(ChatColor.YELLOW+"Click two blocks to determinate the edges");
		return false;
	}


	@Override
	public void end(){
		msgPlayer(ChatColor.YELLOW+"Selected the edges");
		//info();
	}




	@Override
	public Location getLocationMin(){
		return l1;
	}
	@Override
	public Location getLocationMax(){
		return l2;
	}



	

}
