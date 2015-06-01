package pl.cba.knest.ClassicEdit.executor;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.PerspectiveCreation;
import pl.cba.knest.ClassicEdit.selector.HandAreaSelector;
import pl.cba.knest.ClassicEdit.selector.InfiniteDirectionSelector;
import pl.cba.knest.ClassicEdit.selector.WEAreaSelector;


public class PerspectiveExecutor extends AreaExecutor{
	boolean loop = false;
	boolean forcebreak = false;
	double scale = 1;
	double chance = 1;
	double near = 1;
	double far = 512;
	double antialiasstart = 0;
	public void execute() throws ExecutorException{
		super.execute();

		PerspectiveCreation c = new PerspectiveCreation();
		c.setAreaSelector(worldedit?new WEAreaSelector():new HandAreaSelector());
		c.setSourceSelector(new HandAreaSelector());
		c.setPerspectiveSelector(new InfiniteDirectionSelector());
		
		c.setFilling(f);
		c.setLoop(loop);
		c.setForceBreak(forcebreak);
		c.setDropmode(dropmode);
		c.setMask(mask);
		c.setScale(scale);
		c.setChance(chance);
		c.setNear(near);
		c.setFar(far);
		c.setAntialiasstart(antialiasstart);
		c.attach(getSession());
	}

	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'l':
			loop = true;
		return;
		case 'b':
			forcebreak = true;
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
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for near (-n <distance from camera>)");
			}
		return;
		case 'f':
			try{
				far = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for far (-f <distance from camera>)");
			}
		return;
		case 'a':
			try{
				antialiasstart = Double.parseDouble(i.next());
				i.remove();
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for antialias - start (-s <distance from camera>)");
			}
		return;
		}
		super.flag(c, i);
	}
}
