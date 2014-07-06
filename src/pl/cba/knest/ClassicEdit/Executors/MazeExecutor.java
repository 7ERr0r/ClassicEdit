package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.MazeCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class MazeExecutor extends TwoPointExecutor{
	public MazeCreation getCreation(String nick){
		return new MazeCreation(nick);
	}
	private boolean loop;
	private boolean br;

	
	public void execute() throws ExecutorException{
		super.execute();
		
		MazeCreation c = getCreation(p.getName());
		c.setFilling(f);
		c.setDropmode(dropmode);
		c.setLoop(loop);
		c.setBr(br);
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
