package pl.cba.knest.ClassicEdit.Selectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.NotSelectedException;
import pl.cba.knest.ClassicEdit.Creations.AreaCreation;

public class HandAreaSelector extends AreaSelector {
	
	private AreaCreation c;
	private int stage = 0;
	private Location l1;
	private Location l2;
	private boolean first = true;


	public Player getPlayer() {
		return c.getPlayer();
	}



	public Creation getCreation() {
		return c;
	}

	public void setCreation(AreaCreation c) {
		this.c = c;
	}

	@Override
	public boolean selectBlock(Block b){
		Player p = getPlayer();
		if(stage == 0){
			l1 = b.getLocation();
		}else if(stage == 1){
			l2 = b.getLocation();
			
			if(c.getFilling() == null){
				
				if(p != null){
					ItemStack is = p.getItemInHand();
					
					if(is != null && is.getType().isBlock() && is.getType()!=Material.AIR){
						c.setFilling(new Filling(is.getType(), (byte) is.getDurability()));
					}else{
						c.setFilling(new Filling(Material.AIR, (byte) 0));
					}
				}
			}
			
			c.start();
		}else{
			// done
		}
		stage++;
		return stage > 2;
	}
	public void start(){
		msgPlayer(ChatColor.YELLOW+"Click two blocks to determinate the edges");
		first = false;
	}


	@Override
	public void end() {
		//info();
	}

	@Override
	public boolean selectAir(Player p, Action a) {
		return true;
	}



	@Override
	public Location getLocationMin() throws NotSelectedException {
		if(first) start();
		if(l1==null) throw new NotSelectedException();
		return l1;
	}
	@Override
	public Location getLocationMax() throws NotSelectedException {
		if(first) start();
		if(l2==null) throw new NotSelectedException();
		return l2;
	}
	

}
