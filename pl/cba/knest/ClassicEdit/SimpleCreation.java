package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SimpleCreation extends Creation{
	String nick;
	Location l1;
	Location l2;
	World w;
	Filling f;
	
	int taskid;
	
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
	
	int pertick = 1;
	public SimpleCreation(String nick){
		this.nick = nick;
	}
	@Override
	public void run() {
		Bukkit.getScheduler().cancelTask(taskid);
	}

	@Override
	public String getName() {
		return "2-point structure";
	}

	@Override
	public String getPlayerName() {
		return nick;
	}
	public void setDropmode(boolean dropmode){
		this.dropmode = dropmode;
	}

	public void setPoints(Location l1, Location l2){
		this.l1 = l1;
		this.w = l1.getWorld();
		this.l2 = l2;
	}
	public void setFilling(Filling f){
		this.f = f;
	}
	void shedule(){
		taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(ClassicEdit.plugin, this, 1L, 1L);
	}
	@Override
	public void start(){
		maxx = Math.max(l1.getBlockX(), l2.getBlockX());
		maxy = Math.max(l1.getBlockY(), l2.getBlockY());
		maxz = Math.max(l1.getBlockZ(), l2.getBlockZ());
		minx = Math.min(l1.getBlockX(), l2.getBlockX());
		miny = Math.min(l1.getBlockY(), l2.getBlockY());
		minz = Math.min(l1.getBlockZ(), l2.getBlockZ());
		x = minx;
		y = miny;
		z = minz;
		pertick = dropmode?ClassicEdit.droppertick:ClassicEdit.pertick;
		shedule();
	}
	public Filling getFilling(){
		return f;
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
					System.out.print("d"+ile);
					inv.setItem(i, null);
				}else{
					is.setAmount(is.getAmount()-ile);
					return;
				}
			}
		}
		
	}
}
