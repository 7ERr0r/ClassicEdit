package pl.cba.knest.ClassicEdit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PhysicsListener implements Listener {
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent e){
		if(e.isCancelled()) return;
		ClassicEdit.getInstance().getManager().callPhysicsEvent(e);
	}
}
