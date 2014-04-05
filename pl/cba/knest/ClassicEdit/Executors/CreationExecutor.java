package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;

public class CreationExecutor extends PlayerCmdExecutor{
	boolean dropmode = false;
	Filling f = null;
	Creation creation = null;
	public void execute() throws ExecutorException{
		super.execute();
		if(!p.hasPermission("ClassicEdit.create.cuboid")){
			p.sendMessage(ChatColor.RED+"You do not have permission to do this");
			return;
		}
		creation = ClassicEdit.getCuboidManager().getCreation(p);
		if(creation!=null){
			p.sendMessage(ChatColor.RED+"You have an active "+creation.getName()+" running");
			p.sendMessage(ChatColor.GREEN+"Type /p stop to remove it");
			return;
		}
		dropmode = p.getGameMode()!=GameMode.CREATIVE;
		
	}

}
