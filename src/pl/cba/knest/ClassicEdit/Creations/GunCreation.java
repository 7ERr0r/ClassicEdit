package pl.cba.knest.ClassicEdit.Creations;

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
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.util.Vector;


public class GunCreation extends ClickableCreation {
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
				e.setFuseTicks(1);
			}
		}
		public boolean tick(){
			World w = l.getWorld();
			Block b = w.getBlockAt(l);
			
			if(b == null) return false;
			if(ticks<end-len){
				if(b.getType()!=Material.AIR){
					end = ticks+len;
					colide(b);
				}else{
					if(end>ticks){
						l.add(v);
						if(!last.contains(b)){
							fake(b, f.getMaterial(), f.getData());
							last.add(b);
						}
					}
				}
			}
			if(ticks>end-len || last.size()>len) fake(last.poll(), Material.AIR, (byte) 0);
			if(ticks>end) return false;
			ticks++;
			return true;
		}
		@SuppressWarnings("deprecation")
		private void fake(Block b, Material m, byte data){
			if(b == null) return;
			for(Player p : Bukkit.getOnlinePlayers()){
				p.sendBlockChange(b.getLocation(),m,data);
			}
		}
		public void kill(){
			for(Block b : last){
				fake(b, Material.AIR, (byte) 0);
			}
		}
	}
	
	private List<Bolt> bolts = new ArrayList<Bolt>();
	private boolean explode = false;
	private boolean laser = false;
	
	
	@Override
	public void click(Location l){
		Vector v = l.getDirection();
		l.add(0, 1, 0);
		l.add(v);
		l.add(v);
		Bolt bo = new Bolt(l, v, laser?40:10);
		
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
	public void init(){
		super.init();
	}

	@Override
	public void stop(){
		super.stop();
		for(Bolt b : bolts){
			b.kill();
		}
	}

	@Override
	public void onBlockPhysics(BlockPhysicsEvent e){
		
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

	public String getFullName(){
		return getName();
	}
	public void msgStart(){
		msgPlayer(ChatColor.YELLOW+"Starting "+getFullName());
	}
	
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopping gun");
	}


	
}
