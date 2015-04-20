package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.GunCreation;
import pl.cba.knest.ClassicEdit.Selectors.DirectionSelector;
import pl.cba.knest.ClassicEdit.Selectors.InfiniteSelector;

public class GunExecutor extends CreationExecutor {

	boolean explode = false;
	boolean laser = false;

	
	public void execute() throws ExecutorException{
		super.execute();
		/*Creation active = ClassicEdit.getCreationManager().getCreation(p);
		if(active!=null){
			if(active instanceof GunCreation){
				active.stop();
				ClassicEdit.getCreationManager().removeSelector(p);
				return;
			}else{
				throw new ExecutorException(ChatColor.RED+"You have an active "+active.getName()+" running, "+ChatColor.GREEN+"Type /p stop to remove it");
			}
				
		}*/
		GunCreation c = new GunCreation();
		DirectionSelector s = new InfiniteSelector();
		c.setDirectionSelector(s);
		
		c.setExplode(explode);
		c.setLaser(laser);
		c.attach(getSession());
	}
	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'e':
			if(!(s.isOp() || s.hasPermission("ClassicEdit.fun.gun.explode"))){
				throw new ExecutorException(ChatColor.RED+"You don't have permission to use explode flag");
			}
			explode = true;
		return;
		case 'l':
			laser = true;
		return;
		}
		super.flag(c, i);
	}
}
