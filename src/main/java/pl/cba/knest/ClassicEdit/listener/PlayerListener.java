package pl.cba.knest.ClassicEdit.listener;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Session;

public class PlayerListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(ClassicEdit.getCreationManager().hasSession(p)){
			Session s = ClassicEdit.getCreationManager().getSession(p);
			s.handleInteract(e);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(ClassicEdit.getCreationManager().hasSession(p)){
			Session s = ClassicEdit.getCreationManager().getSession(p);
			s.setPlayer(p);
		}
	}
	
}
