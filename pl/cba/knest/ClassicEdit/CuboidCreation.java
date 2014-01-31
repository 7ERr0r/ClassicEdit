package pl.cba.knest.ClassicEdit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;


public class CuboidCreation extends SimpleCreation{
	int x;
	int y;
	int z;
	int maxx;
	int maxy;
	int maxz;
	int minx;
	int miny;
	int minz;
	
	public CuboidCreation(String nick) {
		super(nick);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for(int i = 0; i<100; i++){
			Block b = w.getBlockAt(x,y,z);
			b.setType(f.getMaterial());
			b.setData(f.getData());
			x++;
			if(x>maxx){ 
				x = minx; y++; 
				if(y>maxy){ 
					y = miny; z++;
					if(z>maxz){
						Bukkit.getScheduler().cancelTask(taskid); return;
					}
				}
			}
		}
		
	}

	
	@Override
	public String getName() {
		return "cuboid";
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
		if(f == null){
			f = new Filling(Material.AIR, (byte) 0);
		}
		shedule();
	}


}
