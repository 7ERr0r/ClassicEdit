package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Selector {

	public Selector() {
		
	}
	public void msgPlayer(String msg){
		Player p = getPlayer();
		if(p != null) p.sendMessage(ChatColor.AQUA+"CE: "+msg);
	}
	public abstract Player getPlayer();
	public abstract boolean handleInteract(PlayerInteractEvent e);
	public abstract void start();
	public abstract void end();
}
