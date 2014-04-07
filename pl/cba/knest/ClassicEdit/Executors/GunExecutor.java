package pl.cba.knest.ClassicEdit.Executors;



import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.GunCreation;
import pl.cba.knest.ClassicEdit.Selectors.InfiniteSelector;

public class GunExecutor extends CreationExecutor {

	

	
	public void execute() throws ExecutorException{
		perms("ClassicEdit.create.gun");
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
		
		c.setExplode(flags.contains("e") && perms("ClassicEdit.create.gun.explode"));
		c.setLaser(flags.contains("l"));
		s.start();
	}
}
