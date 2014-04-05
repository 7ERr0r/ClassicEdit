package pl.cba.knest.ClassicEdit;


import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Action a = e.getAction();
		
		if(a == Action.LEFT_CLICK_BLOCK || a == Action.RIGHT_CLICK_BLOCK){
			if(ClassicEdit.getCuboidManager().isSelecting(p)){
				Selector sel = ClassicEdit.getCuboidManager().getSelector(p);
				Block b = e.getClickedBlock();
				if(a == Action.RIGHT_CLICK_BLOCK){
					b = b.getRelative(e.getBlockFace());
				}
				if(!sel.selectBlock(b)){
					e.setCancelled(true);
				}
			}
		}else{
			if(a == Action.LEFT_CLICK_AIR || a == Action.RIGHT_CLICK_AIR){
				if(ClassicEdit.getCuboidManager().isSelecting(p)){
					Selector sel = ClassicEdit.getCuboidManager().getSelector(p);
					
					if(!sel.selectAir(p, a)){
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
