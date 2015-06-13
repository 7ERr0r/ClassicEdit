package pl.cba.knest.ClassicEdit.executor;



import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;

import pl.cba.knest.ClassicEdit.Backend;
import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.creation.Creation;


public class ClassicEditExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException {
		super.execute();
		if(params.size()>0){
			String param = params.remove(0);
			if(param.equals("push")){
				push();
			}else if(param.equals("pull")){
				pull();
			}else if(param.equals("list")){
				list();
			}else if(param.equals("global")){
				global();
			}
		}else{
			msgPlayer("ClassicEdit v"+ClassicEdit.getInstance().getDescription().getVersion()+" by 7ERr0r");
			msgPlayer("Subcommands: list, push, pull, global");
		}
	}
	
	
	public void list(){
		msgPlayer("Active sessions:");
		for(Session sess : ClassicEdit.getCreationManager().getSessions().values()){
			msgPlayer("Session "+(sess.getPlayer()==null?sess.getUUID():sess.getPlayer().getName())+(sess.isPaused()?" [paused]":" "));
			for(Creation c : sess.getCreations()){
				msgPlayer(ChatColor.GREEN+"  "+c.getFullName());
			}
			if(sess.getPending()!=null) msgPlayer(ChatColor.RED+"  "+sess.getPending().getFullName());
		}
	}



	
	public void push() throws ExecutorException {
		if(params.size() == 0){
			throw new ExecutorException("Push requires <global_name> parameter");
		}
		String name = params.remove(0);
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		Session s = ClassicEdit.getCreationManager().getSession(player);
		
		for(Creation c : s.getCreations()){
			c.transfer(gs);
		}
		s.stop();
		msgPlayer("Pushed creations to global session: "+name);
		
	}
	public void pull() throws ExecutorException {
		if(params.size() == 0){
			throw new ExecutorException("Pull requires <global_name> parameter");
		}
		String name = params.remove(0);
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		Session s = ClassicEdit.getCreationManager().getSession(player);
		
		for(Creation c : gs.getCreations()){
			c.transfer(s);
		}
		gs.stop();
		msgPlayer("Pulled creations from global session: "+name);
	}
	
	
	public void global() throws ExecutorException {
		if(params.size() == 0){
			throw new ExecutorException("Global requires <global_name> parameter");
		}
		String name = params.remove(0);
		//Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		//Session s = ClassicEdit.getCreationManager().getSession(player);
		if(params.size() == 0){
			msgPlayer("Subcommands: load, save, pause");
		}else{
			String param = params.remove(0);
			if(param.equals("load")){
				load(name);
			}else if(param.equals("save")){
				save(name);
			}else if(param.equals("pause")){
				pause(name);
			}else if(param.equals("stop")){
				stop(name);
			}
		}
		
	}
	
	
	public void pause(String name){
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		gs.togglePause();
		msgPlayer((gs.isPaused()?"paused ":"unpaused ")+name+" global session");
	}
	public void stop(String name){
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		gs.stop();
		msgPlayer("stopped "+name+" global session");
	}
	public void load(String name){
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		Backend b = ClassicEdit.getInstance().getBackend();
		gs.stop();
		JsonObject root = b.getCreations();
		JsonArray jsess = root.get(name).getAsJsonArray();
		
		for(JsonElement e : jsess){
			JsonObject o = e.getAsJsonObject();
			try {
				Creation c = Creation.deserializeCreation(o);
				c.setSession(gs);
				gs.addCreation(c);
				c.startSelector();
			}catch(ClassNotFoundException ex){
				ex.printStackTrace();
			}
			
		}
		msgPlayer("loaded "+name+" global session");
	}
	
	public void save(String name) throws ExecutorException {
		Session gs = ClassicEdit.getCreationManager().getGlobal(name);
		Backend b = ClassicEdit.getInstance().getBackend();
		
		b.getCreations().remove(name);
		JsonArray jsess = new JsonArray();
		b.getCreations().add(name, jsess);
		for(Creation c : gs.getCreations()){
			JsonObject o = Creation.serializeCreation(c);
			jsess.add(o);
		}
		b.save(name);
		msgPlayer("saved "+name+" global session");
	}

}
