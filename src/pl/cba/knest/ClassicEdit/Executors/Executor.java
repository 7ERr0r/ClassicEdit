package pl.cba.knest.ClassicEdit.Executors;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.cba.knest.ClassicEdit.ExecutorException;


public class Executor{
	CommandSender s;
	List<String> params;
	String flags;
	public Executor(){

	}
	
	public static void msg(CommandSender s, String msg){
		s.sendMessage(ChatColor.GREEN+"CE: "+msg);
	}
	
	public boolean perms(String perm) throws ExecutorException{
		if(!s.hasPermission(perm) && !s.isOp()){
			throw new ExecutorException(ChatColor.RED+"You do not have permission to do this");
		}
		return true;
	}
	public void init(CommandSender s, List<String> params, String flags){
		this.s = s;
		this.params = params;
		this.flags = flags;
	}
	public void execute() throws ExecutorException {

		
	}

}
