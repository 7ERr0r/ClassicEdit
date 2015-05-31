package pl.cba.knest.ClassicEdit;

import org.bukkit.command.CommandSender;

import pl.cba.knest.ClassicEdit.executor.Executor;

public class ExecutorException extends Exception{

	private static final long serialVersionUID = 3190039844166241981L;
	private String msg = null;
	public ExecutorException(){
		this(null);
	}
	public ExecutorException(String msg){
		this.msg = msg;
	}
	public String getMsg(){
		return msg;
	}
	public void send(CommandSender s){
		if(msg!=null) Executor.msg(s, msg);
	}
}
