package pl.cba.knest.ClassicEdit;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public interface Selector {
	public boolean selectBlock(Block b);
	public boolean selectAir(Player p, Action a);
	public void start();
	public void end();
}
