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
	public void execute() throws ExecutorException{
		super.execute();
		if(!p.hasPermission("ClassicEdit.create.cuboid") && !p.isOp()){
			throw new ExecutorException(ChatColor.RED+"You do not have permission to do this");
		}
		Creation active = ClassicEdit.getCuboidManager().getCreation(p);
		if(active!=null){
			throw new ExecutorException(ChatColor.RED+"You have an active "+active.getName()+" running, "+ChatColor.GREEN+"Type /p stop to remove it");
		}
		dropmode = p.getGameMode()!=GameMode.CREATIVE;
		
		if(params.size()==1){
			try{
				f = Filling.parse(params.get(0));
			}catch(Exception e){
				throw new ExecutorException(ChatColor.RED+"Bad block name");
			}
		}
		if(f != null && f.getMaterial() == null){
			throw new ExecutorException(ChatColor.RED+"Invalid block name or id");
		}
		
		
	}

}
