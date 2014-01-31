package pl.cba.knest.ClassicEdit;


import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CuboidCreation extends SimpleCreation{
	
	
	public CuboidCreation(String nick){
		super(nick);
	}
	@SuppressWarnings("deprecation")
	public boolean place(AtomicInteger amount, Player p){
		Block b = w.getBlockAt(x,y,z);
		if(dropmode){
			if(f.getMaterial()!=Material.AIR){
				if(amount.decrementAndGet() < 0){

					p.sendMessage(ChatColor.RED+"You do not have requied materials ("+f+")");
					p.sendMessage(ChatColor.YELLOW+"Supply them and type /pause");
					ClassicEdit.getCuboidManager().pauseCreation(this); return false;
				}
			}
			for(ItemStack drop : b.getDrops()){
				p.getInventory().addItem(drop);
			}

		}
		b.setType(f.getMaterial());
		b.setData(f.getData());
		return true;
	}

	@Override
	public void run() {
		Player p = null;
		AtomicInteger amount = new AtomicInteger(0);
		
		int items = 0;
		if(dropmode){
			p = Bukkit.getPlayer(nick);
			if(p==null){
				ClassicEdit.getCuboidManager().pauseCreation(this); return;
			}
			if(f.getMaterial()!=Material.AIR){
				amount.set(getAmount(f.getMaterial(), f.getData(), p.getInventory()));
				items = amount.get();
			}
		}
		for(int i = 0; i<pertick; i++){
			if(!place(amount, p)) break;
			
			x++;
			if(x>maxx){ 
				x = minx; y++; 
				if(y>maxy){ 
					y = miny; z++;
					if(z>maxz){
						ClassicEdit.getCuboidManager().removeCreation(this); break;
					}
				}
			}
		}
		if(dropmode) setAmount(f.getMaterial(), f.getData(), p.getInventory(), items-amount.get());
	}

	
	@Override
	public String getName() {
		return "cuboid";
	}




}
