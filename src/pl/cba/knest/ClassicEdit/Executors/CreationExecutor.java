package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;


import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Mask;

public class CreationExecutor extends PlayerCmdExecutor{

	Filling f = null;
	Mask mask = null;
	public void execute() throws ExecutorException{
		
		super.execute();
		

		if(flags.contains("m")){
			if(params.size()==0) throw new ExecutorException(ChatColor.RED+"Not enough arguments for mask (-m)");
			mask = new Mask(params.remove(0));
		}
		
		
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
	/*public void checkActive() throws ExecutorException{
		Creation active = ClassicEdit.getCuboidManager().getCreation(p);
		if(active!=null){
			throw new ExecutorException(ChatColor.RED+"You have an active "+active.getName()+" running, "+ChatColor.GREEN+"Type /p stop to remove it");
		}
	}*/

}
