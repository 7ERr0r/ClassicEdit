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
	double sc = 1;


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
		c.setScale(sc);
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
				sc = Double.parseDouble(i.next());
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for perspective (-s <scale>)");
			}
		return;
		}
		super.flag(c, i);
	}
}
