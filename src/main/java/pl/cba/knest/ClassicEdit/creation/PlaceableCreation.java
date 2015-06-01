package pl.cba.knest.ClassicEdit.creation;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Filling;

public abstract class PlaceableCreation extends AreaCreation implements IFilledCreation {
	

	Filling f = new Filling(Material.AIR, (byte) 0);
	AtomicInteger amount;


	
	@SuppressWarnings("deprecation")
	public boolean place(){
		Player p = session.getPlayer();
		Block b = w.getBlockAt(currentx, currenty, currentz);
		Material t = b.getType();
		if(!forcebreak && t==f.getMaterial() && b.getData()==f.getData()) return true;
		boolean place = true;
		
		if(dropmode){
			if(t==Material.BEDROCK || t==Material.ENDER_PORTAL || t==Material.ENDER_PORTAL_FRAME)
				return true;
			
			if(t!=Material.AIR){
				BlockBreakEvent be = new BlockBreakEvent(b, p);
				ClassicEdit.callEventWithoutNCP(be);

				if(!be.isCancelled()){
					if(p!=null && p.getGameMode()!=GameMode.CREATIVE){
						for(ItemStack drop : b.getDrops()){
							HashMap<Integer, ItemStack> out = p.getInventory().addItem(drop);
							for(ItemStack is : out.values()){
								w.dropItemNaturally(b.getLocation(), is);
							}
						}
					}
					b.setType(Material.AIR);
				}else{
					if(getFilling().getMaterial()==Material.AIR){
						place = false;
						if(b.getType()==Material.AIR){
							p.playEffect(b.getLocation(), Effect.STEP_SOUND, t);
							ticksDone++;
							placed++;
						}else{
							cancelled();
							return false;
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
						msgPlayer(ChatColor.RED+"You do not have required materials ("+f+")");
						msgPlayer(ChatColor.YELLOW+"Supply them and type /p or /p stop");
						pause(); 
						return false;
					}
				}else{
					cancelled();
					return false;
				}
			}
		}
		
		if(place){
			if(dropmode){
				w.playEffect(b.getLocation(), Effect.STEP_SOUND, t);
			}
			b.setType(f.getMaterial());
			b.setData(f.getData());
			placed++;
			ticksDone++;

		}

		return true;
	}
	
	@Override
	public void run(){
		Player p = null;
		amount = new AtomicInteger(0);
		
		int items = 0;
		if(dropmode){
			p = session.getPlayer();
			if(p==null){
				pause();
				return;
			}
			if(p!=null && p.getGameMode()==GameMode.CREATIVE){
				amount.set(1000000);
			}else if(f.getMaterial()!=Material.AIR){
				amount.set(getAmount(f.getMaterial(), f.getData(), p.getInventory()));
				items = amount.get();
			}
		}
		super.run();
		if(dropmode && p!=null && p.getGameMode()!=GameMode.CREATIVE){
			setAmount(f.getMaterial(), f.getData(), p.getInventory(), items-amount.get());
		}
	}
	
	public boolean init(){
		if(!super.init()) return false;
		goup = f.getMaterial()!=Material.AIR;
		return true;
	}
	public String getFullName(){
		return getName()+" of "+getFilling();
	}

	
	public void msgEnd(){
		if(!initialised) return;
		if(getFilling()==null){
			msgPlayer(ChatColor.YELLOW+"Created "+sum+" block"+(sum==1?"":"s")+" (filling=null)");
		}else{
			msgPlayer(ChatColor.YELLOW+"Created "+sum+" block"+(sum==1?"":"s")+" of "+getFilling().toString());
		}
	}

	public void setFilling(Filling f){
		this.f = f;
	}
	public Filling getFilling(){
		return f;
	}
}
