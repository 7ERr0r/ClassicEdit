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
		
		if(a != Action.LEFT_CLICK_BLOCK && a != Action.RIGHT_CLICK_BLOCK) return;
		//p.sendMessage("a");
		if(ClassicEdit.getCuboidManager().isSelecting(p)){
			Selector sel = ClassicEdit.getCuboidManager().getSelector(p);
			Block b = e.getClickedBlock();
			if(a == Action.RIGHT_CLICK_BLOCK){
				b = b.getRelative(e.getBlockFace());
			}
			sel.select(b);
			e.setCancelled(true);
		}
	}
}
