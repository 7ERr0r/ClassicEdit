package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;

public class PlayerCmdExecutor extends Executor{

	Player p = null;
	
	public void msgPlayer(String msg){
		msg(p, msg);
	}
	@Override
	public void execute() throws ExecutorException {
		super.execute();
		p = getIfPlayer(s);
		if(params.contains("next") || params.contains("n")){
			Creation c = ClassicEdit.getCuboidManager().getCreation(p);
			if(c == null) throw new ExecutorException(ChatColor.YELLOW+"You don't have any cuboid running");
			c.stop();
			throw new ExecutorException(ChatColor.YELLOW+"Finished "+c.getName());
		}
		if(params.contains("stop") || params.contains("s")){
			Creation c = ClassicEdit.getCuboidManager().getCreation(p);
			if(c == null) throw new ExecutorException(ChatColor.YELLOW+"You don't have any cuboid running");
			c.stop();
			ClassicEdit.getCuboidManager().stopCreations(p);
			ClassicEdit.getCuboidManager().removeSelector(p);
			throw new ExecutorException(ChatColor.YELLOW+"Stopped all creations");
		}
		if(params.contains("help") || params.contains("h")){
			help();
			throw new ExecutorException();
		}
	}
	public void help() {
		msgPlayer("No help");
	}

	private Player getIfPlayer(CommandSender s) throws ExecutorException{
		if(s instanceof Player) return (Player) s;
		throw new ExecutorException(ChatColor.RED+"Only player may call this");
	}
}
