package pl.cba.knest.ClassicEdit.Creations;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Mask;
import pl.cba.knest.ClassicEdit.NotSelectedException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.Selectors.AreaSelector;

public abstract class AreaCreation extends FilledCreation{
	
	public AreaCreation(Session s) {
		super(s);
	}
	boolean started = false;
	boolean dropmode = false;	
	Location l1;
	Location l2;
	World w;
	
	int maxx;
	int maxy;
	int maxz;
	int minx;
	int miny;
	int minz;
	
	int currentx;
	int currenty;
	int currentz;
	
	int width;
	int length;
	int height;
	
	int pertick = 1;
	
	boolean loop = false;
	boolean up = true;
	boolean br = false;
	Mask mask;
	private AreaSelector areaSelector;
	
	public boolean canPlace(int x, int y, int z){
		if(mask!=null){
			Block b = w.getBlockAt(x,y,z);
			if(!mask.contains(b.getType())) return false;
		}
		return true;
	}
	public boolean isInside(int x, int y, int z){
		return x>minx && y>miny && z>minz && x<maxx && y<maxy && z<maxz;
	}
	public boolean isInside(Block b){
		return isInside(b.getX(), b.getY(), b.getZ());
	}
	public boolean isLoop(){
		return loop;
	}
	public void setLoop(boolean loop){
		this.loop = loop;
	}
	public boolean isBr(){
		return br;
	}
	public void setBr(boolean br){
		this.br = br;
	}
	public void setMask(Mask mask){
		this.mask = mask;
	}
	
	public void stop(){
		sum+=placed;
		super.stop();
	}
	
	@Override
	public void run() {
		Player p = null;
		AtomicInteger amount = new AtomicInteger(0);
		
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
		ticksDone = 0;
		for(int i = 0; i<2048; i++){
			
			if(canPlace(currentx,currenty,currentz)){
				if(!place(amount, p)){
					break;
				}
			}
			if(!next()){
				sum += placed;
				if(loop){
					if(placed>0){
						placed = 0;
						init();
						msgPlayer(ChatColor.YELLOW+"Checking... next lap");
					}else{
						pause();
						placed = 0;
						init();
						msgPlayer(ChatColor.YELLOW+"No more blocks to place, paused.");
						break;
					}
				}else{
					placed = 0;
					stop();
					break;
				}
			}
			if(ticksDone>=pertick) break;
		}
		if(dropmode && p!=null && p.getGameMode()==GameMode.CREATIVE){
			setAmount(f.getMaterial(), f.getData(), p.getInventory(), items-amount.get());
		}
	}

	@Override
	public String getName() {
		return "area";
	}


	public void setDropmode(boolean dropmode){
		if(!started) this.dropmode = dropmode;
	}

	
	public void cancelled(){
		msgPlayer(ChatColor.YELLOW+"Event cancelled");
		msgPlayer(ChatColor.YELLOW+"Type /p to unpause or /p stop");
		pause(); 
	}
	
	@SuppressWarnings("deprecation")
	public boolean place(AtomicInteger amount, Player p){
		Block b = w.getBlockAt(currentx,currenty,currentz);
		Material t = b.getType();
		if(!br && t==f.getMaterial() && b.getData()==f.getData()) return true;
		boolean place = true;
		
		if(dropmode){
			if(t==Material.BEDROCK || t==Material.ENDER_PORTAL || t==Material.ENDER_PORTAL_FRAME)
				return true;
			
			if(t!=Material.AIR){
				BlockBreakEvent be = new BlockBreakEvent(b, p);
				ClassicEdit.callEventWithoutNCP(be);

				if(!be.isCancelled()){
					for(ItemStack drop : b.getDrops()){
						HashMap<Integer, ItemStack> out = p.getInventory().addItem(drop);
						for(ItemStack is : out.values()){
							w.dropItemNaturally(b.getLocation(), is);
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
	public void start(){
		try{
			l1 = areaSelector.getLocationMin();
			l2 = areaSelector.getLocationMax();
			w = l1.getWorld();
			super.start();
		}catch(NotSelectedException e){
			
		}
	}
	
	
	
	@Override
	public void init(){
		maxx = Math.max(l1.getBlockX(), l2.getBlockX());
		maxy = Math.max(l1.getBlockY(), l2.getBlockY());
		maxz = Math.max(l1.getBlockZ(), l2.getBlockZ());
		minx = Math.min(l1.getBlockX(), l2.getBlockX());
		miny = Math.min(l1.getBlockY(), l2.getBlockY());
		minz = Math.min(l1.getBlockZ(), l2.getBlockZ());
		width = maxx-minx+1;
		height = maxy-miny+1;
		length = maxz-minz+1;

		up = f.getMaterial()!=Material.AIR;

		pertick = dropmode?ClassicEdit.droppertick:ClassicEdit.pertick;
		started = true;
	}

	int getAmount(Material m, short d, PlayerInventory inv){
		int ile = 0;
		for(ItemStack is : inv.getContents()){
			if(is==null) continue;
			if(isVariant(is, m, d)){
				ile += is.getAmount();
			}
		}
		return ile;
	}
	boolean isVariant(ItemStack is, Material m, short d){
		//if(!m.isBlock()) return false;
		switch(m){
		case RAILS:
		case HOPPER:
		case FURNACE:
		case DISPENSER:
		case DROPPER:
		case PUMPKIN:
		case WOOD_STAIRS:
		case BIRCH_WOOD_STAIRS:
		case SPRUCE_WOOD_STAIRS:
		case JUNGLE_WOOD_STAIRS:
		case ACACIA_STAIRS:
		case DARK_OAK_STAIRS:
		case SANDSTONE_STAIRS:
		case BRICK_STAIRS:
		case COBBLESTONE_STAIRS:
			return is.getType().equals(m);
		case LOG:
		case LOG_2:
			return is.getType().equals(m) && (is.getDurability() & 3)==(d & 3);
		case REDSTONE_WIRE:
			return is.getType()==Material.REDSTONE;
		default:
			return is.getType().equals(m) && d==is.getDurability();
		}
	}
	void setAmount(Material m, short d, PlayerInventory inv, int ile){
		
		for(int i = 0; i<inv.getSize(); i++){
			ItemStack is = inv.getItem(i);
			if(is==null) continue;
			if(isVariant(is, m, d)){
				if(is.getAmount()<=ile){
					ile -= is.getAmount();;
					inv.setItem(i, null);
				}else{
					is.setAmount(is.getAmount()-ile);
					return;
				}
			}
		}
		
	}

	public abstract boolean next();



	public boolean isRunning() {
		return session.getActive() == this;
	}

	@Override
	public void onBlockPhysics(BlockPhysicsEvent e){
		if(e.isCancelled()) return;
		if(isInside(e.getBlock())) e.setCancelled(true);
	}
	public Selector[] getSelectors(){
		return new Selector[]{areaSelector};
	}
	public void setAreaSelector(AreaSelector s) {
		this.areaSelector = s;
		s.setCreation(this);
	}
	
	



}
