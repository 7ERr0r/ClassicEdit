package pl.cba.knest.ClassicEdit.executor;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.PerspectiveCreation;
import pl.cba.knest.ClassicEdit.selector.AreaSelector;
import pl.cba.knest.ClassicEdit.selector.DirectionSelector;
import pl.cba.knest.ClassicEdit.selector.HandAreaSelector;
import pl.cba.knest.ClassicEdit.selector.InfiniteDirectionSelector;
import pl.cba.knest.ClassicEdit.selector.WEAreaSelector;


public class PerspectiveExecutor extends AreaExecutor{
	boolean loop = false;
	boolean br = false;
	double scale = 1;
	double chance = 1;
	double near = 6;
	double far = 1000;
	public void execute() throws ExecutorException{
		super.execute();
		AreaSelector as = worldedit?new WEAreaSelector():new HandAreaSelector();
		AreaSelector ss = new HandAreaSelector();
		DirectionSelector ps = new InfiniteDirectionSelector();
		
		PerspectiveCreation c = new PerspectiveCreation(as, ss, ps);
		c.setFilling(f);
		c.setLoop(loop);
		c.setForceBreak(br);
		c.setDropmode(dropmode);
		c.setMask(mask);
		c.setScale(scale);
		c.setChance(chance);
		c.setNear(near);
		c.setFar(far);
		c.attach(getSession());
	}

	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'l':
			loop = true;
		return;
		case 'b':
			br = true;
		return;
		case 's':
			try{
				scale = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for perspective (-s <scale>)");
			}
		return;
		case 'c':
			try{
				chance = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for chance (-c <chance>)");
			}
		return;
		case 'n':
			try{
				near = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for near (-n <float>)");
			}
		return;
		case 'f':
			try{
				far = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for far (-f <float>)");
			}
		return;
		}
		super.flag(c, i);
	}
}
