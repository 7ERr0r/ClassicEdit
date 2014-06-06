package pl.cba.knest.ClassicEdit;

import java.util.HashSet;

import org.bukkit.Material;

public class Mask {
	private HashSet<Material> e;
	public Mask(String s){
		String[] ss = s.split(",");
		e = new HashSet<Material>();
		for(String ms : ss){
			Material m = Material.matchMaterial(ms);
			if(m!=null){
				e.add(m);
			}
		}
	}
	public boolean contains(Material m){
		return e.contains(m);
	}
}
