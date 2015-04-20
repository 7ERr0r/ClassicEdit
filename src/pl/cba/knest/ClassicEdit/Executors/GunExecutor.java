package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.Creations.GunCreation;
import pl.cba.knest.ClassicEdit.Selectors.DirectionSelector;
import pl.cba.knest.ClassicEdit.Selectors.InfiniteSelector;

public class GunExecutor extends CreationExecutor {

	boolean explode = false;
	boolean laser = false;
	boolean gravity = false;
	boolean real = false;
	
	public void execute() throws ExecutorException{
		super.execute();
		Session sess = ClassicEdit.getCreationManager().getSession(player);
		if(sess.getActive() != null){
			msgPlayer(ChatColor.RED+"You have an active "+sess.getActive()+" running");
			return;
		}
		GunCreation c = new GunCreation();
		DirectionSelector s = new InfiniteSelector();
		c.setDirectionSelector(s);
		c.setExplode(explode);
		c.setLaser(laser);
		c.setGravity(gravity);
		c.setReal(real);
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
		case 'g':
			gravity = true;
		return;
		case 'r':
			real = true;
		return;
		}
		super.flag(c, i);
	}
}
