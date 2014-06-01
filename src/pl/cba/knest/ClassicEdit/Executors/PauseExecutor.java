package pl.cba.knest.ClassicEdit.Executors;



import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;


public class PauseExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException{
		super.execute();
		if(!p.hasPermission("ClassicEdit.pause") && !p.isOp()){
			throw new ExecutorException(ChatColor.RED+"You do not have permission to do this");
		}
		Creation c = ClassicEdit.getCuboidManager().getCreation(p);
		if(c == null){
			throw new ExecutorException(ChatColor.RED+"You don't have any cuboid running");
		}

		if(c!=null){
			if(c.isPause()){
				msgPlayer(ChatColor.YELLOW+"Unpaused "+c.getName());
				c.unpause();
			}else{
				msgPlayer(ChatColor.YELLOW+"Paused "+c.getName());
				c.pause();
			}
				
		}
		
	}

}
