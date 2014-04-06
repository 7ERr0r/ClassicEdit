package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.ChatColor;

public class LineCreation extends TwoPointCreation{
	double dx;
	double dy;
	double dz;
	
	double dist;
	int i = 0;
	
	
	double sx;
	double sy;
	double sz;
	
	public LineCreation(String nick) {
		super(nick);
	}
	public boolean start(){
		super.start();
		dist = l1.distance(l2);
		if((dropmode && dist>2000) || (!dropmode && dist>200000)){
			msgPlayer(ChatColor.RED+"Too many blocks to place");
			return false;
		}
		
		dx = l1.getBlockX();
		dy = l1.getBlockY();
		dz = l1.getBlockZ();
		
		int vx = l2.getBlockX()-l1.getBlockX();
		int vy = l2.getBlockY()-l1.getBlockY();
		int vz = l2.getBlockZ()-l1.getBlockZ();
		
		sx = ((double)vx)/dist;
		sy = ((double)vy)/dist;
		sz = ((double)vz)/dist;
		return true;
	}
	
	@Override
	public boolean next(){
		
		if(i++ > dist) return false;
		dx+=sx;
		dy+=sy;
		dz+=sz;
		
		x = (int) dx;
		y = (int) dy;
		z = (int) dz;
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
