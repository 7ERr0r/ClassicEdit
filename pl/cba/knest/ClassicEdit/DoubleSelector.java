package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DoubleSelector implements Selector{
	private Player p;
	private SimpleCreation c;
	private boolean first = true;
	private Location l1;
	private Location l2;
	public DoubleSelector(Player p, SimpleCreation c){
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

	public void setC(SimpleCreation c) {
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
			c.start();
			ClassicEdit.getCuboidManager().removeSelector(p);
			p.sendMessage(getMessage((byte) 1));
		}
	}
	@Override
	public String getMessage(byte m) {
		switch(m){
		case 0:
			return ChatColor.YELLOW+"Click two blocks to determinate the edges";
		case 1:
			return ChatColor.YELLOW+"Creating "+c.getName()+" of "+c.getFilling().parse();
		}
		return null;
	}

}
