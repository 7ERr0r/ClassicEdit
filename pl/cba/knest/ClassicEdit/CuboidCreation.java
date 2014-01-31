package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class CuboidCreation extends SimpleCreation{
	
	
	public CuboidCreation(String nick){
		super(nick);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Player p = null;
		PlayerInventory inv = null;
		int amount = 0;
		int items = 0;
		if(dropmode){
			p = Bukkit.getPlayer(nick);
			inv = p.getInventory();
			if(f.getMaterial()!=Material.AIR){
				amount = getAmount(f.getMaterial(), f.getData(), inv);
				items = amount;
			}
		}
		for(int i = 0; i<pertick; i++){
			Block b = w.getBlockAt(x,y,z);
			if(dropmode){
				if(f.getMaterial()!=Material.AIR){
					amount--;
					if(amount <= 0){
						p.sendMessage("Za malo surowcow");
						Bukkit.getScheduler().cancelTask(taskid); break;
					}
				}
				for(ItemStack drop : b.getDrops()){
					inv.addItem(drop);
				}

			}
			b.setType(f.getMaterial());
			b.setData(f.getData());
			x++;
			if(x>maxx){ 
				x = minx; y++; 
				if(y>maxy){ 
					y = miny; z++;
					if(z>maxz){
						Bukkit.getScheduler().cancelTask(taskid); break;
					}
				}
			}
		}
		if(dropmode) setAmount(f.getMaterial(), f.getData(), inv, items-amount);
	}

	
	@Override
	public String getName() {
		return "cuboid";
	}




}
