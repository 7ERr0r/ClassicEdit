package pl.cba.knest.ClassicEdit.Creations;


import java.util.HashMap;
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

import pl.cba.knest.ClassicEdit.ClassicEdit;


public class CuboidCreation extends TwoPointCreation{
	
	public boolean dashed = false;
	public CuboidCreation(String nick){
		super(nick);
	}
	@SuppressWarnings("deprecation")
	public boolean place(AtomicInteger amount, Player p){
		Block b = w.getBlockAt(x,y,z);
		if(b.getType()==f.getMaterial() && b.getData()==f.getData()) return true;
		boolean place = true;
		
		//boolean brek = true;
		if(dropmode){
			
			
			
			if(b.getType()==Material.BEDROCK || b.getType()==Material.ENDER_PORTAL || b.getType()==Material.ENDER_PORTAL_FRAME){
				place = false;
			}else{
				if(b.getType()!=Material.AIR){
					

					
					BlockBreakEvent be = new BlockBreakEvent(b, p);
					ClassicEdit.callEventWithoutNCP(be);
					if(!be.isCancelled()){
						for(ItemStack drop : b.getDrops()){
							HashMap<Integer, ItemStack> out = p.getInventory().addItem(drop);
							for(ItemStack is : out.values()){
								w.dropItemNaturally(b.getLocation(), is);
							}
						}
						//b.setType(Material.AIR);
					}else{
						if(getFilling().getMaterial()==Material.AIR){
							place = false;
							if(b.getType()==Material.AIR){
								ppt++;
								placed++;
							}
						}
					}
				}
			}
			if(getFilling().getMaterial()!=Material.AIR){
				ItemStack is = new ItemStack(getFilling().getMaterial(), amount.get()>64?64:amount.get(), getFilling().getData());
				
				BlockPlaceEvent bp = new BlockPlaceEvent(b, b.getState(), b.getRelative(BlockFace.DOWN), is, p, true);
				ClassicEdit.callEventWithoutNCP(bp);
				if(!bp.isCancelled()){
					if(amount.decrementAndGet() < 0){
	
						p.sendMessage(ChatColor.RED+"You do not have requied materials ("+f+")");
						p.sendMessage(ChatColor.YELLOW+"Supply them and type /p or /p stop");
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
			placed++;
			ppt++;
		}

		return true;
	}
	public boolean isInside(){
		return isDashed();
	}
	public boolean isDashed(){
		if(dashed) return (x+y+z)%2==0;
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
		ppt = 0;
		for(int i = 0; i<1024; i++){
			
			if(isInside()){
				if(!place(amount, p)){
					break;
				}
			}
			if(!next()){
				end();
				break;
			}
			if(ppt>=pertick) break;
		}
		if(dropmode) setAmount(f.getMaterial(), f.getData(), p.getInventory(), items-amount.get());
	}

	
	@Override
	public String getName() {
		return "cuboid";
	}
	public void setDashed(boolean dashed){
		this.dashed = dashed;
	}




}
