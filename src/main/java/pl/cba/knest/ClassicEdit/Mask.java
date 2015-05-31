package pl.cba.knest.ClassicEdit;

import java.util.HashMap;

import org.bukkit.Material;

public class Mask {
	private HashMap<Material, Byte> e;
	public Mask(){
		e = new HashMap<Material, Byte>();
	}
	
	public static Mask parseMask(String s){
		Mask mask = new Mask();
		String[] ss = s.split(",");

		for(String ms : ss){
			String[] type = ms.split(":");
			Material m = Material.matchMaterial(type[0]);
			byte data = -1;
			if(type.length>1){
				data = Byte.parseByte(type[1]);
			}
			if(m!=null){
				mask.e.put(m, data);
			}
		}
		return mask;
	}
	public boolean contains(Material m){
		return e.containsKey(m);
	}
}
