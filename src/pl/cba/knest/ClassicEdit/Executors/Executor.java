package pl.cba.knest.ClassicEdit.Executors;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import pl.cba.knest.ClassicEdit.ExecutorException;


public class Executor{
	CommandSender s;
	List<String> params;
	public Executor(){

	}
	
	public static void msg(CommandSender s, String msg){
		s.sendMessage(ChatColor.GREEN+"CE: "+ChatColor.WHITE+msg);
	}
	

	public void init(CommandSender s, List<String> params){
		this.s = s;
		this.params = params;
	}
	public void execute() throws ExecutorException {
		Iterator<String> i = params.iterator();
		while(i.hasNext()){
			String p = i.next();
			if(p.charAt(0)=='-'){
				i.remove();
				p = p.substring(1, p.length());
				for(char c : p.toCharArray()){
					flag(c, i);
				}
			}
		}
	}

	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		default:
			throw new ExecutorException(ChatColor.RED+"Unknown flag "+c);
		}
	}

}
