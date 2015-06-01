package pl.cba.knest.ClassicEdit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
	@Override
	public String toString(){
		String s = "";
		Iterator<Entry<Material, Byte>> i = e.entrySet().iterator();
		while(i.hasNext()){
			Entry<Material, Byte> e = i.next();
			s += e.getKey()+":"+e.getValue();
			if(i.hasNext()) s += ",";
		}
		return s;
	}
	public boolean contains(Material m){
		return e.containsKey(m);
	}
}
