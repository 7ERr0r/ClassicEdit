package pl.cba.knest.ClassicEdit.Creations;


import org.bukkit.ChatColor;


public class CuboidCreation extends TwoPointCreation{
	

	
	public boolean dashed = false;
	
	public CuboidCreation(String nick) {
		super(nick);
	}


	@Override
	public boolean canPlace(int x, int y, int z){
		return isDashed(x,y,z);
	}
	public boolean isDashed(int x, int y, int z){
		if(dashed) return (x+y+z)%2==0;
		return true;
	}
	
	@Override
	public boolean start(){
		super.start();
		long blocks = width*height*length;
		if((dropmode && blocks>20000) || (!dropmode && blocks>2000000)){
			msgPlayer(ChatColor.RED+"Too many blocks to place");
			return false;
		}
		x = minx;
		y = up?miny:maxy;
		z = minz;
		return true;
	}
	@Override
	public boolean next(){
		x++;
		if(x>maxx){ 
			x = minx; if(up) y++; else y--; 
			if((up && y>maxy) || (!up && y<miny)){ 
				if(up) y = miny; else y = maxy; 
				z++;
				if(z>maxz){
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
