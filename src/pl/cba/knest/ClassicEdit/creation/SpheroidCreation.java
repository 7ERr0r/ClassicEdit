package pl.cba.knest.ClassicEdit.creation;

import pl.cba.knest.ClassicEdit.selector.AreaSelector;


public class SpheroidCreation extends CuboidCreation {


	public SpheroidCreation(AreaSelector as) {
		super(as);
	}
	public boolean canPlace(int x,int y,int z){
		if(!super.canPlace(x, y, z)) return false;
		int rx = x-minx;
		int ry = y-miny;
		int rz = z-minz;
		double dx = (((double)(rx+0.5d)/((double)width))-0.5d)*2;
		double dy = (((double)(ry+0.5d)/((double)height))-0.5d)*2;
		double dz = (((double)(rz+0.5d)/((double)length))-0.5d)*2;
		double d = Math.sqrt(Math.pow(Math.abs(dx), 2)+Math.pow(Math.abs(dy), 2)+Math.pow(Math.abs(dz), 2));
		return d<=1;
	}
	@Override
	public String getName() {
		return "spheroid";
	}
}
