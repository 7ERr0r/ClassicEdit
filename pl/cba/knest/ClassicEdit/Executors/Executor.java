package pl.cba.knest.ClassicEdit.Executors;

import java.util.List;

import org.bukkit.command.CommandSender;

import pl.cba.knest.ClassicEdit.ExecutorException;


public class Executor{
	CommandSender s;
	List<String> params;
	String flags;
	public Executor(){

	}
	public void init(CommandSender s, List<String> params, String flags){
		this.s = s;
		this.params = params;
		this.flags = flags;
	}
	public void execute() throws ExecutorException {

		
	}

}
