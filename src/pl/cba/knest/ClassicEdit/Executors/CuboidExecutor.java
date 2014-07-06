package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;


import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class CuboidExecutor extends TwoPointExecutor{
	public CuboidCreation getCreation(String nick){
		return new CuboidCreation(nick);
	}
	boolean dashed = false;
	boolean loop = false;
	boolean br = false;
	public void execute() throws ExecutorException{
		super.execute();
		CuboidCreation c = getCreation(p.getName());
		c.setFilling(f);
		c.setDashed(dashed);
		c.setLoop(loop);
		c.setBr(br);
		c.setDropmode(dropmode);
		c.setMask(mask);
		Selector sel = new TwoPointSelector(p, c);
		ClassicEdit.getCuboidManager().setSelector(p, sel);
		sel.start();
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
		msgPlayer("Usage: /cuboid [-ld] [-m <blocks>] [block]");
		msgPlayer("where [something] is optional, <sth> required");
		msgPlayer("Flags:");
		msgPlayer("-d means dashed");
		msgPlayer("-l means loop");
		msgPlayer("-m <blocks> is mask (replace)");
	}

}
