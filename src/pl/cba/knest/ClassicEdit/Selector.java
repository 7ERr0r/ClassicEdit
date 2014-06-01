package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public abstract class Selector {
	protected Player p;
	public Selector(Player p) {
		this.p = p;
	}
	public void msgPlayer(String msg){
		p.sendMessage(ChatColor.AQUA+"CE: "+msg);
	}
	
	public abstract boolean selectBlock(Block b);
	public abstract boolean selectAir(Player p, Action a);
	public abstract void start();
	public abstract void end();
}
