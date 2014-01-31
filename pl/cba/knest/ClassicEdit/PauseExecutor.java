package pl.cba.knest.ClassicEdit;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PauseExecutor extends Executor{
	public void execute(Player p, List<String> args){
		Creation c = ClassicEdit.getCuboidManager().getCreation(p);
		if(args.contains("stop")){
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
