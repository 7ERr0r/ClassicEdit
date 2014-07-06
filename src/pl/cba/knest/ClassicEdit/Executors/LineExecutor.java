package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.LineCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class LineExecutor extends TwoPointExecutor{
	private boolean loop;
	private boolean br;

	public LineCreation getCreation(String nick){
		return new LineCreation(nick);
	}
	public void execute() throws ExecutorException{
		super.execute();
		
		LineCreation c = getCreation(p.getName());
		c.setFilling(f);
		//c.setDashed(flags.contains("d"));
		c.setLoop(loop);
		c.setBr(br);
		c.setDropmode(dropmode);
		Selector sel = new TwoPointSelector(p, c);
		ClassicEdit.getCuboidManager().setSelector(p, sel);
		sel.start();
	}

	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'l':
			loop = true;
		return;
		case 'b':
			br = true;
		return;
		}
		super.flag(c, i);
	}
}
