package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Mask;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.AreaSelector;

public abstract class AreaCreation extends Creation {
	
	public AreaCreation(AreaSelector as){
		super();
		selectors.add(as); 
		areaSelector = as;
	}

	boolean dropmode = false;	
	boolean loop = false;
	boolean goup = true;
	boolean forcebreak = false;
	boolean nophysics = false;
	
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
	int ticksDone = 0;
	int placed = 0;
	long sum = 0;

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
		return x>=minx-1 && y>=miny-1 && z>=minz-1 && x<=maxx+1 && y<=maxy+1 && z<=maxz+1;
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
	public boolean isForceBreak(){
		return forcebreak;
	}
	public void setForceBreak(boolean br){
		this.forcebreak = br;
	}
	public void setMask(Mask mask){
		this.mask = mask;
	}
	
	public void stop(){
		sum+=placed;
		super.stop();
	}
	
	public abstract boolean place();
	
	@Override
	public void run() {


		ticksDone = 0;
		for(int i = 0; i<8192; i++){
			
			if(canPlace(currentx,currenty,currentz)){
				if(!place()){
					break;
				}
			}
			if(!next()){
				sum += placed;
				if(loop){
					if(placed>0){
						placed = 0;
						init();
						rotate();
						//msgPlayer(ChatColor.YELLOW+"Checking... next lap");
						break;
					}else{
						msgPlayer(ChatColor.YELLOW+"No more blocks to place.");
						pause();
						placed = 0;
						init();
						rotate();
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

	}
	
	@Override
	public String getName() {
		return "area";
	}
	
	
	public void setDropmode(boolean dropmode){
		this.dropmode = dropmode;
	}
	
	
	public void cancelled(){
		msgPlayer(ChatColor.YELLOW+"Event cancelled");
		msgPlayer(ChatColor.YELLOW+"Type /p to unpause or /p stop");
		pause(); 
	}
	
	
	
	
	
	@Override
	public boolean init(){
		l1 = areaSelector.getLocationA();
		if(l1 == null) return false;
		l2 = areaSelector.getLocationB();
		if(l2 == null) return false;
		w = l1.getWorld();
		maxx = Math.max(l1.getBlockX(), l2.getBlockX());
		maxy = Math.max(l1.getBlockY(), l2.getBlockY());
		maxz = Math.max(l1.getBlockZ(), l2.getBlockZ());
		minx = Math.min(l1.getBlockX(), l2.getBlockX());
		miny = Math.min(l1.getBlockY(), l2.getBlockY());
		minz = Math.min(l1.getBlockZ(), l2.getBlockZ());
		width = maxx-minx+1;
		height = maxy-miny+1;
		length = maxz-minz+1;



		pertick = dropmode?ClassicEdit.droppertick:ClassicEdit.pertick;
		return true;
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

	
	public boolean next(){
		currentx++;
		if(currentx>maxx){ 
			currentx = minx; if(goup) currenty++; else currenty--; 
			if(goup?currenty>maxy:currenty<miny){ 
				if(goup) currenty = miny; else currenty = maxy; 
				currentz++;
				if(currentz>maxz){
					return false;
				}
			}
		}
		return true;
	}


	public boolean isRunning() {
		return session.getActive() == this;
	}

	@Override
	public void onBlockPhysics(BlockEvent e){
		if(nophysics && isInside(e.getBlock())){
			if(e instanceof Cancellable){
			((Cancellable) e).setCancelled(true);
			}
		}
	}
	public void setNophysics(boolean nophysics) {
		this.nophysics = nophysics;
	}
	public boolean isNophysics(){
		return nophysics;
	}

	@Override
	public void attach(Session s){
		super.attach(s);
	}
	



}
