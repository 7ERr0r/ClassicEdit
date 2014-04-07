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
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.util.Vector;


public class GunCreation extends InfiniteCreation{
	private List<Bolt> bolts = new ArrayList<Bolt>();
	private boolean explode = false;
	private boolean laser = false;
	private class Bolt{
		private final Location l;
		private final Vector v;
		private int ticks = 0;
		private Queue<Block> last = new LinkedList<Block>();
		private final int len;
		private int end = 200;
		private final GunCreation gun;
		public Bolt(GunCreation gun, Location l, Vector v, int len){
			this.gun = gun;
			this.l = l;
			this.v = v;
			this.len = len;
			
		}
		public void colide(){
			if(gun.explode){
				World w = l.getWorld();
				w.createExplosion(l.clone().add(0, 0.5, 0), 4.0f);
			}
		}
		public boolean tick(){
			World w = l.getWorld();
			Block b = w.getBlockAt(l);
			
			if(b == null) return false;
			if(ticks<end-len){
				if(b.getType()!=Material.AIR){
					end = ticks+len;
					colide();
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
	public GunCreation(String nick) {
		super(nick);
	}
	@Override
	public void click(Location l){
		Vector v = l.getDirection();
		//v.multiply(1.4);
		l.add(0, 1, 0);
		l.add(v);
		l.add(v);
		Bolt bo = new Bolt(this, l, v, laser?40:10);
		
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
	public void start(){
		super.start();
		msgPlayer(ChatColor.YELLOW+"Gun enabled");
	}

	@Override
	public void stop(){
		super.stop();
		for(Bolt b : bolts){
			b.kill();
		}
		msgPlayer(ChatColor.YELLOW+"Gun disabled");

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


	
}
