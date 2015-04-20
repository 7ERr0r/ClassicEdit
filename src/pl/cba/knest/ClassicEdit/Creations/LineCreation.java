package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.ChatColor;

public class LineCreation extends AreaCreation {
	double dx;
	double dy;
	double dz;
	
	double dist;
	int i = 0;
	
	
	double sx;
	double sy;
	double sz;
	
	public void init(){
		super.init();
		dx = l1.getBlockX()+0.5;
		dy = l1.getBlockY()+0.5;
		dz = l1.getBlockZ()+0.5;
		
		int vx = l2.getBlockX()-l1.getBlockX();
		int vy = l2.getBlockY()-l1.getBlockY();
		int vz = l2.getBlockZ()-l1.getBlockZ();
		
		dist = Math.max(width, height);
		dist = Math.max(dist, length);
		msgPlayer(dist+"");
		if((dropmode && dist>2000) || (!dropmode && dist>200000)){
			msgPlayer(ChatColor.RED+"Too many blocks to place");
			stop();
			return;
		}
		
		sx = ((double)vx)/dist;
		sy = ((double)vy)/dist;
		sz = ((double)vz)/dist;
	}
	
	@Override
	public boolean next(){
		
		if(i++ > dist) return false;
		currentx = (int) dx;
		currenty = (int) dy;
		currentz = (int) dz;
		dx+=sx;
		dy+=sy;
		dz+=sz;
		return true;
		
	}
	@Override
	public String getName(){
		return "line";
	}
	@Override
	public void run() {
		super.run();
		
	}

}
