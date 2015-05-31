package pl.cba.knest.ClassicEdit;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Clipboard{
	
	public byte[] blocks;
	public byte[] blockData;
	public int width;
	public int length;
	public int height;
	
	private Clipboard(){
		
	}
	//public NBTTagList tileEnts;
	public Clipboard(byte[] blocks, byte[] blockData, int width, int length, int height){
		this.blocks = blocks;
		this.blockData = blockData;
		this.width = width;
		this.length = length;
		this.height = height;
	}
	
	@SuppressWarnings("deprecation")
	public static void fromWorld(Location l1, Location l2){
		int minx = Math.min(l1.getBlockX(), l2.getBlockX());
		int miny = Math.min(l1.getBlockY(), l2.getBlockY());
		int minz = Math.min(l1.getBlockZ(), l2.getBlockZ());
		int maxx = Math.max(l1.getBlockX(), l2.getBlockX());
		int maxy = Math.max(l1.getBlockY(), l2.getBlockY());
		int maxz = Math.max(l1.getBlockZ(), l2.getBlockZ());
		Clipboard c = new Clipboard();
		c.width = maxx-minx+1;
		c.height = maxy-miny+1;
		c.length = maxz-minz+1;
		c.blocks = new byte[c.width*c.height*c.length];
		c.blockData = new byte[c.width*c.height*c.length];
		int i = 0;
		for(int x = minx; x<=maxx; x++){
			for(int y = miny; y<=maxy; y++){
				for(int z = minz; z<=maxz; z++){
					Block b = l1.getWorld().getBlockAt(x, y, z);
					c.blocks[i] = (byte) b.getTypeId();
					c.blockData[i] = b.getData();
					i++;
				}
			}
		}
	}
	
	
	public byte getBlockIdAt(int index){
		return blocks[index];
	}
	public byte getBlockDataAt(int index){
		return blockData[index];
	}
}