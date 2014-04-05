package pl.cba.knest.ClassicEdit.Executors;



import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;


public class PauseExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException{
		super.execute();
		if(!p.hasPermission("ClassicEdit.pause") && !p.isOp()){
			p.sendMessage(ChatColor.RED+"You do not have permission to do this");
			return;
		}
		Creation c = ClassicEdit.getCuboidManager().getCreation(p);
		if(c == null){
			p.sendMessage(ChatColor.RED+"You don't have any cuboid running");
			return;
		}
		if(params.contains("stop")){
			ClassicEdit.getCuboidManager().removeCreation(c);
			p.sendMessage(ChatColor.YELLOW+"Stopped "+c.getName());
		}else{
			if(c!=null){
				if(c.getTaskid()==0){
					ClassicEdit.getCuboidManager().unpauseCreation(c);
				}else{
					ClassicEdit.getCuboidManager().pauseCreation(c);
				}
				
			}
		}
	}

}
