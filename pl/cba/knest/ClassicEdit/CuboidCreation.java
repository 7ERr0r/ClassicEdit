package pl.cba.knest.ClassicEdit;


import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;


public class CuboidCreation extends SimpleCreation{
	
	
	public CuboidCreation(String nick){
		super(nick);
	}
	@SuppressWarnings("deprecation")
	public boolean place(AtomicInteger amount, Player p){
		Block b = w.getBlockAt(x,y,z);
		boolean place = true;
		//boolean brek = true;
		if(dropmode){
			if(b.isLiquid() || b.getType()==Material.BEDROCK || b.getType()==Material.ENDER_PORTAL || b.getType()==Material.ENDER_PORTAL_FRAME){
				place = false;
			}else{
				if(b.getType()!=Material.AIR){
					BlockBreakEvent be = new BlockBreakEvent(b, p);
					ClassicEdit.callEventWithoutNCP(be);
					if(!be.isCancelled()){
						for(ItemStack drop : b.getDrops()){
							p.getInventory().addItem(drop);
						}
					}else{
						//brek = false;
					}
				}
			}
			if(f.getMaterial()!=Material.AIR){
				ItemStack is = new ItemStack(f.getMaterial(), amount.get()>64?64:amount.get(), f.getData());
				
				BlockPlaceEvent bp = new BlockPlaceEvent(b, b.getState(), b.getRelative(BlockFace.DOWN), is, p, true);
				ClassicEdit.callEventWithoutNCP(bp);
				if(!bp.isCancelled()){
					if(amount.decrementAndGet() < 0){
	
						p.sendMessage(ChatColor.RED+"You do not have requied materials ("+f+")");
						p.sendMessage(ChatColor.YELLOW+"Supply them and type /pause");
						ClassicEdit.getCuboidManager().pauseCreation(this); return false;
					}
				}else{
					place = false;
				}
			}

		}
		
		if(place){
			b.setType(f.getMaterial());
			b.setData(f.getData());
		}

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
