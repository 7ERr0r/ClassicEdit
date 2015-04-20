package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;



import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Creations.SpheroidCreation;
import pl.cba.knest.ClassicEdit.Selectors.HandAreaSelector;


public class SpheroidExecutor extends AreaExecutor {

	boolean dashed = false;
	boolean loop = false;
	boolean br = false;
	public void execute() throws ExecutorException{
		super.execute();
		
		CuboidCreation c = new SpheroidCreation();
		c.setFilling(f);
		c.setDashed(dashed);
		c.setLoop(loop);
		c.setForceBreak(br);
		c.setDropmode(dropmode);
		c.setMask(mask);
		c.setAreaSelector(new HandAreaSelector());
		c.attach(getSession());
	}
	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'd':
			dashed = true;
		return;
		case 'l':
			loop = true;
		return;
		case 'b':
			br = true;
		return;
		}
		super.flag(c, i);
	}
	public void help() {
		msgPlayer("Usage: /spheroid [-ld] [-m <blocks>] [block]");
		msgPlayer("where [something] is optional, <sth> required");
		msgPlayer("Flags:");
		msgPlayer("-d means dashed");
		msgPlayer("-l means loop");
		msgPlayer("-m <blocks> is mask (replace)");
	}

}
