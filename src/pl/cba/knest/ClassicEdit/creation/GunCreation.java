package pl.cba.knest.ClassicEdit.creation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.util.Vector;

import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.selector.DirectionSelector;


public class GunCreation extends DirectionalCreation implements IFilledCreation {
	
	public GunCreation(DirectionSelector ds) {
		super(ds);
	}
	private class Bolt{
		private final Location l;
		private final Vector v;
		private int ticks = 0;
		private Queue<Block> last = new LinkedList<Block>();
		private final int len;
		private int end = 200;
		public Bolt(Location l, Vector v, int len){
			this.l = l;
			this.v = v;
			this.len = len;
			
		}
		public void colide(Block b){
			if(explode){
				Location ex = b.getLocation();
				World w = ex.getWorld();
				TNTPrimed e = (TNTPrimed) w.spawnEntity(ex, EntityType.PRIMED_TNT);
				e.setFuseTicks(0);
			}
		}
		public boolean tick(){
			World w = l.getWorld();
			Block b = w.getBlockAt(l);
			
			if(b == null) return false;
			if(ticks<end-len){
				if(b.getType()!=Material.AIR && b.getType()!=filling.getMaterial()){
					end = ticks+len;
					colide(b);
				}else{
					if(end>ticks){
						l.add(v);
						if(!last.contains(b)){
							place(b, filling.getMaterial(), filling.getData());
							last.add(b);
						}
					}
				}
			}
			if(ticks>end-len || last.size()>len) place(last.poll(), Material.AIR, (byte) 0);
			if(ticks>end) return false;
			if(gravity){
				v.add(gravector);
				if(v.length()>1) v.normalize();
			}
			ticks++;
			return true;
		}
		@SuppressWarnings("deprecation")
		private void place(Block b, Material m, byte data){
			if(b == null) return;
			if(real){
				b.setType(m);
				b.setData(data);
			}else{
				fake(b, m, data);
			}
		}
		@SuppressWarnings("deprecation")
		private void fake(Block b, Material m, byte data){
			for(Player p : Bukkit.getOnlinePlayers()){
				p.sendBlockChange(b.getLocation(),m,data);
			}
		}
		public void kill(){
			for(Block b : last){
				place(b, Material.AIR, (byte) 0);
			}
		}
	}
	private static final Vector gravector = new Vector(0,-0.02,0);
	private List<Bolt> bolts = new ArrayList<Bolt>();
	private boolean explode = false;
	private boolean laser = false;
	private boolean gravity = false;
	private boolean real = false;
	private Filling filling;
	
	@Override
	public void onClick(){
		Location l = getPlayer().getLocation();
		Vector v = l.getDirection();
		l.add(0, 1, 0);
		l.add(v);
		l.add(v);
		Bolt bo = new Bolt(l, v.clone(), laser?40:10);
		
		bolts.add(bo);
		bo.tick();
	}
	@Override
	public void run(){
		ArrayList<Bolt> toremove = new ArrayList<Bolt>();
		for(Bolt b : bolts){
			if(!(b.tick() && (!laser || (b.tick() && b.tick() && b.tick())))){//tick 4 times if its laser
				toremove.add(b);
				b.kill();
			}
		}
		bolts.removeAll(toremove);
		
	}

	@Override
	public String getName(){
		return "gun";
	}

	@Override
	public boolean init(){
		if(!super.init()) return false;
		return true;
	}

	@Override
	public void stop(){
		super.stop();
		for(Bolt b : bolts){
			b.kill();
		}
	}

	@Override
	public void onBlockPhysics(BlockEvent e){
		
	}
	public boolean isExplode() {
		return explode;
	}
	public void setExplode(boolean explode) {
		this.explode = explode;
	}
	public boolean isLaser() {
		return laser;
	}
	public void setLaser(boolean laser) {
		this.laser = laser;
	}
	public boolean isGravity() {
		return gravity;
	}
	public void setGravity(boolean gravity) {
		this.gravity  = gravity;
	}
	public boolean isReal() {
		return real;
	}
	public void setReal(boolean real) {
		this.real = real;
	}
	@Override
	public String getFullName(){
		return getName();
	}
	@Override
	public void msgStart(){
		msgPlayer(ChatColor.YELLOW+"Starting "+getFullName());
	}
	@Override
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopping gun");
	}
	@Override
	public boolean isUseless(){
		return true;
	}
	@Override
	public Filling getFilling() {
		return filling;
	}
	@Override
	public void setFilling(Filling f) {
		this.filling = f;
	}




	
}
