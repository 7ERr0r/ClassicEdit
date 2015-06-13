package pl.cba.knest.ClassicEdit;

import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;

public abstract class Backend {
	public JsonObject creations = new JsonObject();
	public abstract void close();

	public JsonObject getCreations(){
		return creations;
	}

	public abstract void save(String name);
	public abstract void load();
}
