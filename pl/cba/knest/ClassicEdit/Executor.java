package pl.cba.knest.ClassicEdit;

import java.util.Arrays;
import java.util.List;

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
			List<String> arg = Arrays.asList(args);
			execute(p, arg);
		}else{
			s.sendMessage("Only player may call this");
		}
		return true;
	}

	public void execute(Player p, List<String> args) {
		p.sendMessage("nothing");
	}
	
}
