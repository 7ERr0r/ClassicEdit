package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.TwoPointCreation;

public class TwoPointSelector extends Selector{
	
	private TwoPointCreation c;
	private boolean first = true;
	private Location l1;
	private Location l2;
	public TwoPointSelector(Player p, TwoPointCreation c){
		super(p);
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

	public void setC(TwoPointCreation c) {
		this.c = c;
	}

	@Override
	public boolean selectBlock(Block b){
		if(first){
			l1 = b.getLocation();
			first = false;
		}else{
			l2 = b.getLocation();
			
			if(c.getFilling() == null){
				
				if(p != null){
					ItemStack is = p.getItemInHand();
					
					ClassicEdit.getCuboidManager().removeSelector(p);
					
					if(is != null && is.getType().isBlock() && is.getType()!=Material.AIR){
						c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
					}else{
						c.setFilling(new Filling(Material.AIR, (byte) 0));
					}
				}
			}
			
			c.setPoints(l1, l2);
			ClassicEdit.getCuboidManager().removeSelector(p);
			c.start();
			if(c.isRunning()){
				info();
			}
		}
		return false;
	}
	public void start(){
		msgPlayer(ChatColor.YELLOW+"Click two blocks to determinate the edges");
	}
	public void info(){
		//msgPlayer(ChatColor.YELLOW+"Creating "+c.getName()+" of "+c.getFilling());
	}

	@Override
	public void end() {
		//info();
	}

	@Override
	public boolean selectAir(Player p, Action a) {
		return true;
	}

		
	

}
