package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.AreaSelector;
import pl.cba.knest.ClassicEdit.selector.DirectionSelector;


public class PerspectiveCreation extends CuboidCreation implements IClickableCreation {
	public PerspectiveCreation(AreaSelector as, AreaSelector ss, DirectionSelector ps) {
		super(as);
		sourceSelector = ss;
		perspectiveSelector = ps;
		selectors.add(sourceSelector);
		selectors.add(perspectiveSelector);
	}
	public static final Filling AIR = new Filling(Material.AIR, (byte) 0);
	protected Location per;
	protected double scale = 1;
	protected AreaSelector sourceSelector;
	protected DirectionSelector perspectiveSelector;
	protected Location s1;
	protected Location s2;
	protected int centerx;
	protected int centery;
	protected int centerz;

	
	@SuppressWarnings("deprecation")
	private Filling getExpectedAt(int x, int y){
		int ex = centerx+x;
		int ez = centerz+y;
		if(ex < minx || ex > maxx) return null;
		if(ez < minz || ez > maxz) return null;
		Block b = w.getBlockAt(ex, miny, ez);
		return new Filling(b.getType(), b.getData());
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
		this.scale = s;
	}
	public boolean canPlace(int x,int y,int z){
		if(!super.canPlace(x, y, z)) return false;
		Vector v = new Vector(x-per.getX(),y-per.getY(),z-per.getZ());
		v = rotate(v,(per.getPitch()/180f)*Math.PI,(per.getYaw()/180f)*Math.PI);
		if(v.getZ()<0) return false;
		double sc = scale/v.getZ();
		int rx = (int) (-sc*v.getX());
		int ry = (int) (-sc*v.getY());
		Filling f = getExpectedAt(rx, ry);
		if(f == null) return false;
		this.f = f;
		return true;
	}

	@Override
	public String getName() {
		return "perspective";
	}


	
	@Override
	public boolean init(){
		if(!super.init()) return false;
		s1 = sourceSelector.getLocationMin();
		if(s1 == null) return false;
		s2 = sourceSelector.getLocationMax();
		if(s2 == null) return false;
		if(per == null) return false;
		centerx = minx+width/2;
		centery = minx+height/2;
		centerz = minx+length/2;
		return true;
	}
	
	@Override
	public void attach(Session s){
		super.attach(s);

	}

	@Override
	public void onClick() {
		Location l = getPlayer().getLocation();
		this.per = l;
	}
}
