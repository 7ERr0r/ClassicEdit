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
	public String parse(){
		return m+(data==0?"":":"+data);
	}
}
