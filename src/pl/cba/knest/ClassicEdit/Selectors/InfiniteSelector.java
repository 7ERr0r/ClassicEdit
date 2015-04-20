package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Creations.ClickableCreation;

public class InfiniteSelector extends DirectionSelector {
	private ClickableCreation c;

	public InfiniteSelector(){

	}


	public Creation getCreation() {
		return c;
	}

	public void setCreation(ClickableCreation c) {
		this.c = c;
	}
	public void start(){
		msgPlayer(ChatColor.YELLOW+"Click air to determiante direction");
	}


	@Override
	public void end() {
		
	}

	@Override
	public boolean handleInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR) return false;
		if(p != null){
			ItemStack is = p.getItemInHand();
			
			
			
			if(is != null && is.getType().isBlock()){
				c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
			}else{
				c.setFilling(new Filling(Material.AIR, (byte) 0));
			}
			if(c.getFilling() == null || c.getFilling().getMaterial()==Material.AIR){
				msgPlayer(ChatColor.RED+"Material cannot be air");
			}else{
				c.click(p.getLocation());
			}
		}
		return false;
	}



	@Override
	public Player getPlayer() {
		return c.getPlayer();
	}
		
	

}
