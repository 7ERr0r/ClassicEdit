package pl.cba.knest.ClassicEdit.Executors;



import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.MazeCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class MazeExecutor extends TwoPointExecutor{
	public MazeCreation getCreation(String nick){
		return new MazeCreation(nick);
	}
	public void execute() throws ExecutorException{
		perms("ClassicEdit.create.maze");
		super.execute();
		
		MazeCreation c = getCreation(p.getName());
		c.setFilling(f);
		c.setDropmode(dropmode);
		c.setLoop(flags.contains("l"));
		Selector sel = new TwoPointSelector(p, c);
		ClassicEdit.getCuboidManager().setSelector(p, sel);
		sel.start();
	}


}
