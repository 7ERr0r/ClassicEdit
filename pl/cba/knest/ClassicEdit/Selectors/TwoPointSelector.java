package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.TwoPointCreation;

public class TwoPointSelector implements Selector{
	private Player p;
	private TwoPointCreation c;
	private boolean first = true;
	private Location l1;
	private Location l2;
	public TwoPointSelector(Player p, TwoPointCreation c){
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

	public void setC(TwoPointCreation c) {
		this.c = c;
	}
	public void select(Block b){
		if(first){
			l1 = b.getLocation();
			first = false;
		}else{
			l2 = b.getLocation();
			
			if(c.getFilling() == null){
				
				if(p != null){
					ItemStack is = p.getItemInHand();
					if(!p.isOnline()){
						ClassicEdit.getCuboidManager().removeSelector(p);
						return;
					}
					if(is != null && is.getType().isBlock()){
						c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
					}else{
						c.setFilling(new Filling(Material.AIR, (byte) 0));
					}
				}
			}
			
			c.setPoints(l1, l2);
			ClassicEdit.getCuboidManager().removeSelector(p);
			if(ClassicEdit.getCuboidManager().runCreation(p, c)){
				p.sendMessage(getMessage((byte) 1));
			}
		}
	}
	@Override
	public String getMessage(byte m) {
		switch(m){
		case 0:
			return ChatColor.YELLOW+"Click two blocks to determinate the edges";
		case 1:
			return ChatColor.YELLOW+"Creating "+c.getName()+" of "+c.getFilling();
		}
		return null;
	}

}
