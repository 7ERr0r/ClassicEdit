package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Creations.AreaCreation;

public class HandAreaSelector extends AreaSelector {
	
	private AreaCreation c;
	private int stage = 0;
	private Location l1;
	private Location l2;


	public Player getPlayer(){
		return c.getPlayer();
	}



	public Creation getCreation(){
		return c;
	}

	public void setCreation(AreaCreation c){
		this.c = c;
	}

	@Override
	public boolean handleInteract(PlayerInteractEvent e){
		if(e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
		Block b = e.getClickedBlock();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			b = b.getRelative(e.getBlockFace());
		}
		Player p = getPlayer();
		if(stage == 0){
			l1 = b.getLocation();
			e.setCancelled(true);
		}else if(stage == 1){
			l2 = b.getLocation();
			
			if(c.getFilling() == null){
				
				if(p != null){
					ItemStack is = p.getItemInHand();
					
					if(is != null && is.getType().isBlock() && is.getType()!=Material.AIR){
						c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
					}else{
						c.setFilling(new Filling(Material.AIR, (byte) 0));
					}
				}
			}
			e.setCancelled(true);
		}else{
			// done
		}
		stage++;
		return stage > 2;
	}
	public void start(){
		msgPlayer(ChatColor.YELLOW+"Click two blocks to determinate the edges");
	}


	@Override
	public void end(){
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
