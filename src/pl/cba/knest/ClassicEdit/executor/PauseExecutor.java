package pl.cba.knest.ClassicEdit.executor;



import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.Creation;


public class PauseExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException{
		super.execute();
		Creation c = ClassicEdit.getCreationManager().getSession(player).getActive();
		if(c == null){
			throw new ExecutorException(ChatColor.RED+"You don't have any cuboid running");
		}

		if(c!=null){
			if(c.isPaused()){
				msgPlayer(ChatColor.YELLOW+"Unpaused "+c.getName());
				c.unpause();
			}else{
				msgPlayer(ChatColor.YELLOW+"Paused "+c.getName());
				c.pause();
			}
				
		}
		
	}

}
