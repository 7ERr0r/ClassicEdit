package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.InfiniteCreation;

public class InfiniteSelector implements Selector{
	private Player p;
	private InfiniteCreation c;

	public InfiniteSelector(Player p, InfiniteCreation c){
		this.p = p;
		this.c = c;
	}

	public Player getPlayer() {
		return p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	public Creation getC() {
		return c;
	}

	public void setC(InfiniteCreation c) {
		this.c = c;
	}
	public void start(){
		p.sendMessage(ChatColor.YELLOW+"Gun enabled");
	}


	@Override
	public void end() {
		p.sendMessage(ChatColor.YELLOW+"Gun disabled");
	}

	@Override
	public boolean selectAir(Player p, Action a) {
		if(p != null){
			ItemStack is = p.getItemInHand();
			
			
			
			if(is != null && is.getType().isBlock()){
				c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
			}else{
				c.setFilling(new Filling(Material.AIR, (byte) 0));
			}
			if(!ClassicEdit.getCuboidManager().isRunning(p)){
				ClassicEdit.getCuboidManager().runCreation(p, c);
			}
			c.click(p.getLocation());
		}
		return false;
	}

	@Override
	public boolean selectBlock(Block b) {
		return true;
	}
		
	

}
