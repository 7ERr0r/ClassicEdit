package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import pl.cba.knest.ClassicEdit.ExecutorException;

public class PlayerCmdExecutor extends Executor{

	Player p;
	@Override
	public void execute() throws ExecutorException {
		super.execute();
		p = getIfPlayer(s);
	}
	private Player getIfPlayer(CommandSender s) throws ExecutorException{
		if(s instanceof Player) return (Player) s;
		throw new ExecutorException(ChatColor.RED+"Only player may call this");
	}
}
