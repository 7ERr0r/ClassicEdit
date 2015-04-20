package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.Location;
import org.bukkit.util.Vector;


public class PerspectiveCreation extends CuboidCreation {
	Location per;
	boolean[][] data;
	int dw = 57;
	int dh = 7;
	double s = 1;
	
	public PerspectiveCreation() {
		
		String a = "000000000000000000000000000000000000000000000000000000000" +
				   "001000100111110001111001111100111110011110001110001110000" +
				   "001100100100000010000000010000100000010001000100010001000" +
				   "001010100111000001110000010000111000011110000100011111000" +
				   "001001100100000000001000010000100000010001000100010001000" +
				   "001000100100000010001000010000100000010001000100010001000" +
				   "001000100111110001110000010000111110010001001110010001000";
		data = new boolean[dh][dw];
		for(int iy = 0; iy<dh; iy++){
			for(int ix = 0; ix<dw; ix++){
				data[iy][ix]=a.charAt(ix+iy*dw)=='1';
				
			}
			
		}
		
	}
	private double[] rotate2d(double x, double y,double a){
		return new double[]{Math.cos(a)*x-Math.sin(a)*y,Math.sin(a)*x+Math.cos(a)*y};
	}
	private Vector rotate(Vector v, double a, double b){
		double[] p;
		v = v.clone();
		p = rotate2d(v.getX(),v.getZ(), a);
		v.setX(p[0]);
		v.setZ(p[1]);
		p = rotate2d(v.getX(),v.getY(), 0);
		v.setX(p[0]);
		v.setY(p[1]);
		p = rotate2d(v.getZ(),v.getY(), b);
		v.setZ(p[0]);
		v.setY(p[1]);
		return v;
	}
	public void setScale(double s){
		this.s = s;
	}
	public boolean canPlace(int x,int y,int z){
		if(!super.canPlace(x, y, z)) return false;
		Vector v = new Vector(x-per.getX(),y-per.getY(),z-per.getZ());
		v = rotate(v,(per.getPitch()/180f)*Math.PI,(per.getYaw()/180f)*Math.PI);
		if(v.getZ()<0) return false;
		double sc = s/v.getZ();
		int rx = (int) (-sc*v.getX());
		int ry = (int) (-sc*v.getY());
		rx += dw/2;
		ry += dh/2;
		if(rx>=0 && rx<dw && ry>=0 && ry<dh){
			return data[ry][rx];
		}
		return false;
	}
	
	@Override
	public String getName() {
		return "perspective";
	}

	public void setPerspective(Location per) {
		this.per = per;
	}
}
