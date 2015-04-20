package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public abstract class Selector {

	public Selector() {
		
	}
	public void msgPlayer(String msg){
		Player p = getPlayer();
		if(p != null) p.sendMessage(ChatColor.AQUA+"CE: "+msg);
	}
	public abstract Player getPlayer();
	public abstract boolean selectBlock(Block b);
	public abstract boolean selectAir(Player p, Action a);
	public abstract void start();
	public abstract void end();
}
