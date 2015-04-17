package pl.cba.knest.ClassicEdit.Executors;


import java.util.Iterator;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Mask;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Creations.TwoPointCreation;

public class TwoPointExecutor extends CreationExecutor {
	boolean dropmode = false;
	Mask mask = null;
	public TwoPointCreation getCreation(String nick){
		return new CuboidCreation(nick);
	}
	
	public void execute() throws ExecutorException{
		super.execute();
		dropmode = !p.hasPermission("ClassicEdit.dropmode");
	}
	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'm':
			if(i.hasNext()){
				mask = new Mask(i.next());
				i.remove();
			}else throw new ExecutorException(ChatColor.RED+"Not enough arguments for mask (-m <blocks>)");
		return;
		}
		super.flag(c, i);
	}
}
