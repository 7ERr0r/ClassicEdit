package pl.cba.knest.ClassicEdit.selector;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.creation.ICreation;
import pl.cba.knest.ClassicEdit.creation.IFilledCreation;

public abstract class Selector {

	public Selector() {
		
	}
	public void msgPlayer(String msg){
		Player p = getPlayer();
		if(p != null) p.sendMessage(ChatColor.AQUA+"CE: "+msg);
	}
	public void setFillingAuto(){
		if(getCreation() instanceof IFilledCreation){
			IFilledCreation fc = (IFilledCreation) getCreation();
			if(fc.getFilling() == null && getPlayer() != null){
				ItemStack is = getPlayer().getItemInHand();
				
				if(is != null && is.getType().isBlock() && is.getType()!=Material.AIR){
					fc.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
				}else{
					fc.setFilling(new Filling(Material.AIR, (byte) 0));
				}
			}
		}
	}
	
	public abstract ICreation getCreation();
	public abstract Player getPlayer();
	public abstract boolean handleInteract(PlayerInteractEvent e);
	public abstract boolean start(ICreation c);
	public abstract void end();
}
