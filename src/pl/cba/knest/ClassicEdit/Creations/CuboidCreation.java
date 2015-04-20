package pl.cba.knest.ClassicEdit.Creations;


import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Session;


public class CuboidCreation extends AreaCreation {
	

	
	public boolean dashed = false;

	
	public CuboidCreation(Session s) {
		super(s);
	}


	@Override
	public boolean canPlace(int x, int y, int z){
		return isDashed(x,y,z) && super.canPlace(x,y,z);
	}
	public boolean isDashed(int x, int y, int z){
		if(dashed) return (x+y+z)%2==0;
		return true;
	}
	
	@Override
	public void init(){
		super.init();
		if(Math.max(width, height)>ClassicEdit.getLimit(session.getPlayer(), dropmode)){
			msgPlayer(ChatColor.RED+"Too many blocks to place");
			stop();
			return;
		}
		currentx = minx;
		currenty = up?miny:maxy;
		currentz = minz;
	}
	@Override
	public boolean next(){
		currentx++;
		if(currentx>maxx){ 
			currentx = minx; if(up) currenty++; else currenty--; 
			if((up && currenty>maxy) || (!up && currenty<miny)){ 
				if(up) currenty = miny; else currenty = maxy; 
				currentz++;
				if(currentz>maxz){
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public String getName() {
		return "cuboid";
	}
	public void setDashed(boolean dashed){
		this.dashed = dashed;
	}

}
