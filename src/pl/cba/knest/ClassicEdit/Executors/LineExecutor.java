package pl.cba.knest.ClassicEdit.Executors;



import java.util.Iterator;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.LineCreation;
import pl.cba.knest.ClassicEdit.Selectors.AreaSelector;
import pl.cba.knest.ClassicEdit.Selectors.HandAreaSelector;

public class LineExecutor extends AreaExecutor{
	private boolean loop;
	private boolean br;


	public void execute() throws ExecutorException{
		super.execute();
		
		LineCreation c = new LineCreation();
		c.setFilling(f);
		c.setLoop(loop);
		c.setForceBreak(br);
		c.setDropmode(dropmode);
		AreaSelector s = new HandAreaSelector();
		c.setAreaSelector(s);
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
