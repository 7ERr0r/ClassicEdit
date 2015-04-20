package pl.cba.knest.ClassicEdit.Executors;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.PerspectiveCreation;
import pl.cba.knest.ClassicEdit.Selectors.AreaSelector;
import pl.cba.knest.ClassicEdit.Selectors.HandAreaSelector;


public class PerspectiveExecutor extends AreaExecutor{
	Location per =	new Location(null,0,0,0,0,0);
	boolean loop = false;
	boolean br = false;
	double sc = 1;


	public void execute() throws ExecutorException{
		super.execute();
		PerspectiveCreation c = new PerspectiveCreation();
		c.setFilling(f);
		c.setLoop(loop);
		c.setBr(br);
		c.setDropmode(dropmode);
		c.setMask(mask);
		c.setPerspective(per);
		c.setScale(sc);
		AreaSelector s = new HandAreaSelector();
		c.setAreaSelector(s);
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
		case 'p':
			try{
				double x = Double.parseDouble(i.next());
				double y = Double.parseDouble(i.next());
				double z = Double.parseDouble(i.next());
				float yaw = Float.parseFloat(i.next());
				float pitch = Float.parseFloat(i.next());
				per = new Location(null,x,y,z,yaw,pitch);
				
			}catch(NoSuchElementException e){
				throw new ExecutorException(ChatColor.RED+"Not enough arguments for perspective (-p <x> <y> <z> <yaw> <pitch>)");
			}
		return;
		}
		super.flag(c, i);
	}
}
