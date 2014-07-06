package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.GunCreation;
import pl.cba.knest.ClassicEdit.Selectors.InfiniteSelector;

public class GunExecutor extends CreationExecutor {

	boolean explode = false;
	boolean laser = false;

	
	public void execute() throws ExecutorException{
		super.execute();
		Creation active = ClassicEdit.getCuboidManager().getCreation(p);
		if(active!=null){
			if(active instanceof GunCreation){
				active.stop();
				ClassicEdit.getCuboidManager().removeSelector(p);
				return;
			}else{
				throw new ExecutorException(ChatColor.RED+"You have an active "+active.getName()+" running, "+ChatColor.GREEN+"Type /p stop to remove it");
			}
				
		}
		GunCreation c = new GunCreation(p.getName());
		Selector s = new InfiniteSelector(p, c);
		
		ClassicEdit.getCuboidManager().setSelector(p, s);
		
		c.setExplode(explode);
		c.setLaser(laser);
		s.start();
	}
	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'e':
			if(!(p.isOp() || p.hasPermission("ClassicEdit.fun.gun.explode"))){
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
