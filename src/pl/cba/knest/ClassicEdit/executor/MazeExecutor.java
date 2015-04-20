package pl.cba.knest.ClassicEdit.executor;



import java.util.Iterator;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.MazeCreation;
import pl.cba.knest.ClassicEdit.selector.AreaSelector;
import pl.cba.knest.ClassicEdit.selector.HandAreaSelector;

public class MazeExecutor extends AreaExecutor{
	private boolean loop;
	private boolean br;

	
	public void execute() throws ExecutorException{
		super.execute();
		MazeCreation c = new MazeCreation();
		c.setFilling(f);
		c.setDropmode(dropmode);
		c.setLoop(loop);
		c.setForceBreak(br);
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
