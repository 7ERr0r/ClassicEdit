package pl.cba.knest.ClassicEdit;

import org.bukkit.block.Block;

public interface Selector {
	public void select(Block b);
	public String getMessage(byte m);
}
