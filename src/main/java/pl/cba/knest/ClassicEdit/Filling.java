package pl.cba.knest.ClassicEdit;

import org.bukkit.Material;

public class Filling {
	private Material m;
	private byte data;
	public Filling(Material m, byte data){
		this.m = m;
		this.data = data;
	}
	public Material getMaterial() {
		return m;
	}
	public void setMaterial(Material m) {
		this.m = m;
	}
	public byte getData() {
		return data;
	}
	public void setData(byte data) {
		this.data = data;
	}
	public String toString(){
		return m.toString().toLowerCase()+(data==0?"":":"+data);
	}
	public static Filling parse(String str){
		String[] s = str.split(":");
		Material m = Material.matchMaterial(s[0]);
		byte data = 0;
		if(s.length>=2){
			data = Byte.parseByte(s[1]);
		}
		return new Filling(m, data);
	}
	
	public boolean equals(Filling f){
		return m==f.m && data==f.data;
	}
	
	
}
