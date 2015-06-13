package pl.cba.knest.ClassicEdit.creation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.com.google.gson.ExclusionStrategy;
import org.bukkit.craftbukkit.libs.com.google.gson.FieldAttributes;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonPrimitive;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Mask;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.Selector;

public abstract class Creation implements ICreation, Runnable {
	
	public static Gson gson;
	static {
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.addSerializationExclusionStrategy(new ExclusionStrategy() {
	        @Override
	        public boolean shouldSkipField(FieldAttributes f) {
	            return  f.getDeclaredType() != Boolean.TYPE &&
	            		f.getDeclaredType() != Integer.TYPE && 
	            		f.getDeclaredType() != Double.TYPE &&
	            		f.getDeclaredType() != Float.TYPE &&
	            		f.getDeclaredType() != Location.class &&
	            		f.getDeclaredType() != World.class &&
	            		f.getDeclaredType() != Filling.class &&
	            		!Selector.class.isAssignableFrom(f.getDeclaredClass()) &&
	            		f.getDeclaredType() != Material.class &&
	            		f.getDeclaredType() != Mask.class &&
					    f.getDeclaredType() != Byte.TYPE;
	        }
	
	        @Override
	        public boolean shouldSkipClass(Class<?> type) {
	            return false;
	        }
	    });
		gsonb.registerTypeAdapter(Location.class, new TypeAdapter<Location>() {
	
			@Override
			public Location read(JsonReader r) throws IOException {
				double x = 0,y = 0,z = 0,yaw = 0,pitch = 0;
				String world = null;
				r.beginObject();
				while(r.hasNext()){
					String name = r.nextName();
	
					if(name.equals("x")){
						x = r.nextDouble();
					}else if(name.equals("y")){
						y = r.nextDouble();
					}else if(name.equals("z")){
						z = r.nextDouble();
					}else if(name.equals("yaw")){
						yaw = r.nextDouble();
					}else if(name.equals("pitch")){
						pitch = r.nextDouble();
					}else if(name.equals("world")){
						world = r.nextString();
					}else{
						r.skipValue();
					}
				}
				r.endObject();
				return new Location(Bukkit.getWorld(world), x, y, z, (float)yaw, (float)pitch);
			}
	
			@Override
			public void write(JsonWriter w, Location l) throws IOException {
				w.beginObject();
				w.name("x").value(l.getX());
				w.name("y").value(l.getY());
				w.name("z").value(l.getZ());
				w.name("yaw").value(l.getYaw());
				w.name("pitch").value(l.getPitch());
				w.name("world").value(l.getWorld().getName());
				w.endObject();
			}
		});
		
		gsonb.registerTypeHierarchyAdapter(World.class, new TypeAdapter<World>() {
			
			@Override
			public World read(JsonReader r) throws IOException {
				String world = r.nextString();
				return Bukkit.getWorld(world);
			}
	
			@Override
			public void write(JsonWriter w, World world) throws IOException {
				w.value(world.getName());
			}
		});
		
		
		gsonb.registerTypeHierarchyAdapter(Mask.class, new TypeAdapter<Mask>() {
			
			@Override
			public Mask read(JsonReader r) throws IOException {
				String m = r.nextString();
				return m==null?null:Mask.parseMask(m);
			}
	
			@Override
			public void write(JsonWriter w, Mask m) throws IOException {
				w.value(m==null?null:m.toString());
			}
		});
		
		gson = gsonb.create();
		
	
	}
	
	
	
	
	
	protected Session session;
	protected boolean started;
	protected boolean initialised;
	protected Queue<Selector> selectors;

	public Creation(){
		selectors = new LinkedList<Selector>();
	}

	public abstract String getName();
	public abstract void onBlockPhysics(BlockEvent e);
	public abstract boolean init();
	public boolean isInitialised(){
		return initialised;
	}
	public void tick(){
		if(!initialised){
			if(init()){
				initialised = true;
				start();
			}
			return;
		}
		run();
	}
	public Queue<Selector> getSelectors(){
		return selectors;
	}
	
	public Session getSession(){
		return session;
	}
	public Player getPlayer(){
		return session.getPlayer();
	}
	
	public void msgPlayer(String msg){
		Player p = getPlayer();
		if(p!=null) p.sendMessage(ChatColor.GOLD+"CE: "+msg);
	}


	public String getFullName(){
		return getName();
	}
	public void start(){
		if(!started){
			started = true;
			session.addCreation(this);
			session.setPending(null);
			msgStart();
		}else{
			ClassicEdit.log("Warning: start() was invoked more than once");
			msgPlayer("Warning: start() was invoked more than once");
		}
	}

	public void stop(){
		msgEnd();
		session.removeCreation(this);
		if(session.getPending()==this) session.setPending(null);

	}
	public void rotate(){
		session.removeCreation(this);
		session.addCreation(this);
	}
	public void pause(){
		session.pause();
	}
	public void unpause(){
		session.unpause();
	}
	public void togglePause(){
		session.togglePause();
	}
	public boolean isPaused() {
		return session.isPaused();
	}
	
	public void msgStart(){
		boolean active = session.isActive(this);
		msgPlayer(ChatColor.YELLOW+(active?"Creating ":"Queued ")+getFullName());
	}
	
	public void msgEnd(){
		msgPlayer(ChatColor.YELLOW+"Stopped "+getFullName());
	}
	
	public void handleInteract(PlayerInteractEvent e){
		Selector s = selectors.peek();
		if(s != null && s.handleInteract(e)){
			nextSelector();
		}
	}

	public void nextSelector(){
		Selector s = selectors.poll();
		if(s != null){
			s.end();
			startSelector();
		}
	}
	public void startSelector(){
		Selector s = selectors.peek();
		if(s != null){
			if(s.start(this)){
				nextSelector();
			}
		}
	}
	public void setSession(Session session){
		this.session = session;
	}
	public void attach(Session session){
		this.session = session;
		session.setPending(this);
		startSelector();
	}
	public void transfer(Session session){
		this.session = session;
		session.addCreation(this);
	}
	public boolean isUseless(){
		return false;
	}
	
	public String toString(){
		return getName();
	}

	public static Creation deserializeCreation(JsonObject json) throws ClassNotFoundException{
		Class<?> clazz = Class.forName(json.get("type").getAsString());
		json.remove("type");
		return (Creation) gson.fromJson(json, clazz);
	}

	public static JsonObject serializeCreation(Creation creation){
		JsonElement e = gson.toJsonTree(creation);
		JsonObject o = e.getAsJsonObject();
		o.add("type", new JsonPrimitive(creation.getClass().getName()));
		return o;
	}

}
