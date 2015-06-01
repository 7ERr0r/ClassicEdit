package pl.cba.knest.ClassicEdit.creation;


public class SpheroidCreation extends CuboidCreation {


	public boolean canPlace(int x,int y,int z){
		if(!super.canPlace(x, y, z)) return false;
		int rx = x-minx;
		int ry = y-miny;
		int rz = z-minz;
		double dx = (2*rx+1d)/width-1;
		double dy = (2*ry+1d)/height-1;
		double dz = (2*rz+1d)/length-1;
		double d = Math.pow(Math.abs(dx), 2)+Math.pow(Math.abs(dy), 2)+Math.pow(Math.abs(dz), 2);
		return d<=1;
	}
	@Override
	public String getName() {
		return "spheroid";
	}
}
