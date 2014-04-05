package pl.cba.knest.ClassicEdit.Executors;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Creations.TwoPointCreation;

public class TwoPointExecutor extends CreationExecutor {
	public TwoPointCreation getCreation(String nick){
		return new CuboidCreation(nick);
	}
	
	public void execute() throws ExecutorException{
		super.execute();

		if(params.size()==1){
			try{
				f = Filling.parse(params.get(0));
			}catch(Exception e){
				throw new ExecutorException(ChatColor.RED+"Bad block name");
			}
		}
		if(f != null && f.getMaterial() == null){
			throw new ExecutorException(ChatColor.RED+"Invalid block name or id");
		}
		
	}
}
