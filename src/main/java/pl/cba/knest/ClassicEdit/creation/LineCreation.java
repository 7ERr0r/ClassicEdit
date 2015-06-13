package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.ChatColor;

public class LineCreation extends PlaceableCreation {

	
	double dx;
	double dy;
	double dz;
	
	double distance;
	int linei = 0;
	
	
	double sx;
	double sy;
	double sz;
	
	public boolean init(){
		if(!super.init()) return false;
		dx = l1.getBlockX()+0.5;
		dy = l1.getBlockY()+0.5;
		dz = l1.getBlockZ()+0.5;
		
		int vx = l2.getBlockX()-l1.getBlockX();
		int vy = l2.getBlockY()-l1.getBlockY();
		int vz = l2.getBlockZ()-l1.getBlockZ();
		
		distance = Math.max(width, height);
		distance = Math.max(distance, length);
		if((dropmode && distance>2000) || (!dropmode && distance>200000)){
			msgPlayer(ChatColor.RED+"Too many blocks to place");
			stop();
			return false;
		}
		
		sx = ((double)vx)/distance;
		sy = ((double)vy)/distance;
		sz = ((double)vz)/distance;
		return true;
	}
	
	@Override
	public boolean next(){
		
		if(linei++ > distance) return false;
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
