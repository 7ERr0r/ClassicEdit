package pl.cba.knest.ClassicEdit.creation;



import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;


public class CuboidCreation extends PlaceableCreation {
	
	public boolean dashed = false;

	
	@Override
	public boolean canPlace(int x, int y, int z){
		return isDashed(x,y,z) && super.canPlace(x,y,z);
	}
	
	public boolean isDashed(int x, int y, int z){
		if(dashed) return (x+y+z)%2==0;
		return true;
	}
	
	@Override
	public boolean init(){
		if(!super.init()) return false;
		if(Math.max(width, height)>ClassicEdit.getLimit(session.getPlayer(), dropmode)){
			msgPlayer(ChatColor.RED+"Too many blocks");
			stop();
			return false;
		}
		currentx = minx;
		currenty = goup?miny:maxy;
		currentz = minz;
		return true;
	}

	@Override
	public String getName(){
		return "cuboid";
	}
	public void setDashed(boolean dashed){
		this.dashed = dashed;
	}




}
