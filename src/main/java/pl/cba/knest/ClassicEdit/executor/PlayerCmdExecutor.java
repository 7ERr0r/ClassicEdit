package pl.cba.knest.ClassicEdit.executor;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;




import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Session;

public class PlayerCmdExecutor extends Executor {

	Player player = null;
	
	
	public Session getSession(){
		return ClassicEdit.getCreationManager().getSession(player);
	}
	
	public void msgPlayer(String msg){
		msg(player, msg);
	}
	@Override
	public void execute() throws ExecutorException {
		super.execute();
		player = getIfPlayer(s);
	}
	public void help() {
		msgPlayer("No help");
	}

	public Player getIfPlayer(CommandSender s) throws ExecutorException{
		if(s instanceof Player) return (Player) s;
		throw new ExecutorException(ChatColor.RED+"Only player may call this");
	}
}
