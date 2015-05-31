package pl.cba.knest.ClassicEdit.executor;



import java.util.Iterator;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.LineCreation;
import pl.cba.knest.ClassicEdit.selector.HandAreaSelector;

public class LineExecutor extends AreaExecutor{
	private boolean loop;
	private boolean br;


	public void execute() throws ExecutorException{
		super.execute();
		
		LineCreation c = new LineCreation(new HandAreaSelector());
		c.setFilling(f);
		c.setLoop(loop);
		c.setForceBreak(br);
		c.setDropmode(dropmode);
		c.attach(getSession());
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
