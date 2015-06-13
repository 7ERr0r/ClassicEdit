package pl.cba.knest.ClassicEdit.executor;


import org.bukkit.ChatColor;





import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.creation.Creation;

public class CreationExecutor extends PlayerCmdExecutor {

	protected Filling f = null;
	protected boolean worldedit;


	
	public void execute() throws ExecutorException{
		super.execute();
		
		if(params.contains("next") || params.contains("n")){
			Creation c = ClassicEdit.getCreationManager().getCreation(player);
			if(c == null) throw new ExecutorException(ChatColor.YELLOW+"You don't have any cuboid running");
			c.stop();
			throw new ExecutorException(ChatColor.YELLOW+"Finished "+c.getName());
		}
		if(params.contains("stop") || params.contains("s")){
			Session s = ClassicEdit.getCreationManager().getSession(player);
			//if(c == null) throw new ExecutorException(ChatColor.YELLOW+"You don't have any cuboid running");
			s.stop();
			throw new ExecutorException(ChatColor.YELLOW+"Session stopped");
		}
		if(params.contains("help") || params.contains("h")){
			help();
			throw new ExecutorException();
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
		worldedit = label.charAt(0) == '/';
		if(worldedit){
			if(!ClassicEdit.isWorldEdit()){
				throw new ExecutorException(ChatColor.RED+"WorldEdit not found");
			}
		}
		Session sess = ClassicEdit.getCreationManager().getSession(player);
		sess.skipUseless();
		
	}


}
