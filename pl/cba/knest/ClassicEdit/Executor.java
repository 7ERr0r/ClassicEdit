package pl.cba.knest.ClassicEdit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Executor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(s instanceof Player){
			Player p = (Player) s;
			// TODO: Parsing flags and addictional cmd args
			execute(p, args);
		}else{
			s.sendMessage("Only player may call this");
		}
		return true;
	}

	public void execute(Player p, String[] args) {
		p.sendMessage("nothing");
	}
	
}
