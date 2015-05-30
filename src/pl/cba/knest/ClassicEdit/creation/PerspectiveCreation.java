package pl.cba.knest.ClassicEdit.creation;

import java.util.Random;

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
	protected double chance = 1;
	protected AreaSelector sourceSelector;
	protected DirectionSelector perspectiveSelector;
	protected Location s1;
	protected Location s2;
	protected int sminx;
	protected int sminy;
	protected int sminz;
	protected int smaxx;
	protected int smaxy;
	protected int smaxz;
	protected int swidth;
	protected int sheight;
	protected int slength;
	protected int scenterx;
	protected int scentery;
	protected int scenterz;
	protected Random r = new Random();
	protected double near = 1;
	protected double far = 512;
	protected double antialiasstart = 0;
	protected boolean antialias;

	
	@SuppressWarnings("deprecation")
	private Filling getExpectedAt(int x, int y){
		//ClassicEdit.log(x+" "+sminy+" "+y);
		int ex = scenterx+x;
		int ez = scenterz+y;
		if(ex < sminx || ex > smaxx) return AIR;
		if(ez < sminz || ez > smaxz) return AIR;

		Block b = w.getBlockAt(ex, sminy, ez);
		return new Filling(b.getType(), b.getData());
	}
	public double[] rotate2d(double x, double y, double a){
		return new double[]{Math.cos(a)*x-Math.sin(a)*y,Math.sin(a)*x+Math.cos(a)*y};
	}
	private Vector rotate3d(Vector v, double a, double b, double c){
		v = v.clone();
		// WTF??? rotations are z -> y -> x ??? why? IDK... it works that way (it's a reverse perspective)
		v = new Vector(
				v.getX()*Math.cos(c)-v.getY()*Math.sin(c), 
				v.getX()*Math.sin(c)+v.getY()*Math.cos(c), 
				v.getZ()); //R z
		v = new Vector(
				v.getX()*Math.cos(b)+v.getZ()*Math.sin(b), 
				v.getY(), 
				-v.getX()*Math.sin(b)+v.getZ()*Math.cos(b)); //R y
		v = new Vector(
				v.getX(), 
				v.getY()*Math.cos(a)-v.getZ()*Math.sin(a), 
				v.getY()*Math.sin(a)+v.getZ()*Math.cos(a)); //R x
		return v;
	}
	public void setScale(double scale){
		this.scale = scale;
	}
	public void setAntialiasstart(double antialiasstart){
		this.antialiasstart = antialiasstart;
	}
	public Filling calculateFilling(double x, double y, double z){
		Vector v = new Vector(x-per.getX(),y-(per.getY()+1.6),z-per.getZ());
		v = rotate3d(v,-(per.getPitch()/180f)*Math.PI,(per.getYaw()/180f)*Math.PI, 0);
		antialias = v.getZ()<antialiasstart;
		if(v.getZ()<near || v.getZ()>far){
			return AIR;
		}
		double sc = scale/v.getZ();
		int rx = (int) Math.round(-sc*v.getX());
		int ry = (int) Math.round(-sc*v.getY());
		return getExpectedAt(rx, ry);
	}
	public Filling getPerspectiveFilling(int x, int y, int z){
		if(chance != 1 && r.nextDouble()>chance){
			return AIR;
		}
		Filling f = calculateFilling(x+0.5, y+0.5, z+0.5);
		int passnear = 0;
		boolean antialias = this.antialias;
		Filling pass = antialias?f:AIR;
		for(double fx = 0; fx<=1; fx++){
			for(double fy = 0; fy<=1; fy++){
				for(double fz = 0; fz<=1; fz++){
					if(calculateFilling(x+fx, y+fy, z+fz).equals(pass)) passnear++; 
				}
			}
		}
		if(antialias){
			if(passnear<8) return AIR;
		}else{
			if(passnear>0) return AIR;
		}
		return f;
	}
	public boolean canPlace(int x, int y, int z){
		if(!super.canPlace(x, y, z)) return false;
		Filling f = getPerspectiveFilling(x, y, z);
		if(!forcebreak && f.getMaterial() == Material.AIR) return false;
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
		s1 = sourceSelector.getLocationA();
		if(s1 == null) return false;
		s2 = sourceSelector.getLocationB();
		if(s2 == null) return false;
		if(per == null) return false;
		smaxx = Math.max(s1.getBlockX(), s2.getBlockX());
		smaxy = Math.max(s1.getBlockY(), s2.getBlockY());
		smaxz = Math.max(s1.getBlockZ(), s2.getBlockZ());
		sminx = Math.min(s1.getBlockX(), s2.getBlockX());
		sminy = Math.min(s1.getBlockY(), s2.getBlockY());
		sminz = Math.min(s1.getBlockZ(), s2.getBlockZ());
		swidth = smaxx-sminx+1;
		sheight = smaxy-sminy+1;
		slength = smaxz-sminz+1;
		scenterx = sminx+swidth/2;
		scentery = sminy+sheight/2;
		scenterz = sminz+slength/2;
		return true;
	}
	
	@Override
	public void attach(Session s){
		super.attach(s);
	}
	@Override
	public String getFullName(){
		return getName();
	}
	public double getChance(){
		return chance;
	}
	public void setChance(double chance){
		this.chance = chance;
	}
	@Override
	public void onClick() {
		Location l = getPlayer().getLocation();
		this.per = l;
	}
	public void setNear(double near) {
		this.near = near;
	}
	public void setFar(double far) {
		this.far = far;
	}
}
