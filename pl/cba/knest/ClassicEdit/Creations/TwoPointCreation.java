package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import pl.cba.knest.ClassicEdit.ClassicEdit;

public abstract class TwoPointCreation extends FilledCreation{
	public TwoPointCreation(String nick) {
		super(nick);
	}
	boolean started = false;
	
	Location l1;
	Location l2;
	World w;

	
	
	
	boolean dropmode = false;
	
	
	int x;
	int y;
	int z;
	int maxx;
	int maxy;
	int maxz;
	int minx;
	int miny;
	int minz;
	
	int width;
	int length;
	int height;
	
	int pertick = 1;
	
	int placed = 0;
	int ppt = 0;
	
	boolean up = true;

	@Override
	public void run() {
		ClassicEdit.getCuboidManager().removeCreation(this);
	}

	@Override
	public String getName() {
		return "2-point structure";
	}


	public void setDropmode(boolean dropmode){
		if(!started) this.dropmode = dropmode;
	}

	public void setPoints(Location l1, Location l2){
		if(started) return;
		this.l1 = l1;
		this.w = l1.getWorld();
		this.l2 = l2;
	}

	@Override
	public boolean start(){
		maxx = Math.max(l1.getBlockX(), l2.getBlockX());
		maxy = Math.max(l1.getBlockY(), l2.getBlockY());
		maxz = Math.max(l1.getBlockZ(), l2.getBlockZ());
		minx = Math.min(l1.getBlockX(), l2.getBlockX());
		miny = Math.min(l1.getBlockY(), l2.getBlockY());
		minz = Math.min(l1.getBlockZ(), l2.getBlockZ());
		width = maxx-minx+1;
		height = maxy-miny+1;
		length = maxz-minz+1;
		long blocks = width*height*length;
		if((dropmode && blocks>20000) || (!dropmode && blocks>2000000)){
			Bukkit.getPlayer(nick).sendMessage(ChatColor.RED+"Too many blocks to place");
			return false;
		}
		up = f.getMaterial()!=Material.AIR;
		x = minx;
		y = up?miny:maxy;
		z = minz;
		pertick = dropmode?ClassicEdit.droppertick:ClassicEdit.pertick;
		started = true;
		return true;
	}

	int getAmount(Material m, short d, PlayerInventory inv){
		int ile = 0;
		for(ItemStack is : inv.getContents()){
			if(is==null) continue;
			if(is.getType()==m && (!m.isBlock() || is.getDurability()==d)){
				ile += is.getAmount();
			}
		}
		return ile;
	}
	void setAmount(Material m, short d, PlayerInventory inv, int ile){
		
		for(int i = 0; i<inv.getSize(); i++){
			ItemStack is = inv.getItem(i);
			if(is==null) continue;
			if(is.getType()==m && is.getDurability()==d){
				if(is.getAmount()<=ile){
					ile -= is.getAmount();
					//System.out.print("d"+ile);
					inv.setItem(i, null);
				}else{
					is.setAmount(is.getAmount()-ile);
					return;
				}
			}
		}
		
	}

	public boolean next(){
		x++;
		if(x>maxx){ 
			x = minx; if(up) y++; else y--; 
			if((up && y>maxy) || (!up && y<miny)){ 
				if(up) y = miny; else y = maxy; 
				z++;
				if(z>maxz){
					ClassicEdit.getCuboidManager().removeCreation(this); return false;
				}
			}
		}
		return true;
	}
	public void end(){
		Player p = Bukkit.getPlayer(nick);
		if(p!=null){
			p.sendMessage(ChatColor.YELLOW+"Created "+placed+" block"+(placed==1?"":"s")+" of "+getFilling().toString());
		}
	}

}
