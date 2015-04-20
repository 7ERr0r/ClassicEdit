package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;




import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Session;

public class CreationExecutor extends PlayerCmdExecutor {

	Filling f = null;

	public Session getSession(){
		return ClassicEdit.getCreationManager().getSession(player);
	}
	
	public void execute() throws ExecutorException{
		super.execute();
		if(params.size()>=1){
			try{
				f = Filling.parse(params.remove(0));
			}catch(Exception e){
				
			}
		}
		if(f != null && f.getMaterial() == null){
			throw new ExecutorException(ChatColor.RED+"Invalid block name or id");
		}
		
		
	}


}
