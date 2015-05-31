package pl.cba.knest.ClassicEdit.selector;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.creation.IClickableCreation;
import pl.cba.knest.ClassicEdit.creation.ICreation;
import pl.cba.knest.ClassicEdit.creation.IFilledCreation;

public class InfiniteDirectionSelector extends DirectionSelector {
	private ICreation creation;




	public ICreation getCreation() {
		return creation;
	}


	public boolean start(ICreation c){
		this.creation = c;
		msgPlayer(ChatColor.YELLOW+"Click air to determiante direction");
		return false;
	}


	@Override
	public void end() {
		
	}

	public void callClick(){
		if(creation instanceof IClickableCreation){
			((IClickableCreation) creation).onClick();
		}
	}
	
	@Override
	public boolean handleInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_AIR) return false;
		if(p != null){
			ItemStack is = p.getItemInHand();
			
			
			if(creation instanceof IFilledCreation){
				IFilledCreation fc = (IFilledCreation) creation;
				if(is != null && is.getType().isBlock()){
					fc.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
				}else{
					fc.setFilling(new Filling(Material.AIR, (byte) 0));
				}
				if(fc.getFilling() == null || fc.getFilling().getMaterial()==Material.AIR){
					msgPlayer(ChatColor.RED+"Material cannot be air");
				}else{
					callClick();
				}
			}else{
				callClick();
			}
		}
		return false;
	}



	@Override
	public Player getPlayer() {
		return creation.getPlayer();
	}
		
	

}
