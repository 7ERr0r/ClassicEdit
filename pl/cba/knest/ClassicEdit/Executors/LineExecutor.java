package pl.cba.knest.ClassicEdit.Executors;



import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.LineCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class LineExecutor extends TwoPointExecutor{
	public LineCreation getCreation(String nick){
		return new LineCreation(nick);
	}
	public void execute() throws ExecutorException{
		perms("ClassicEdit.create.line");
		super.execute();
		
		LineCreation c = getCreation(p.getName());
		c.setFilling(f);
		//c.setDashed(flags.contains("d"));
		c.setDropmode(dropmode);
		Selector sel = new TwoPointSelector(p, c);
		ClassicEdit.getCuboidManager().setSelector(p, sel);
		sel.start();
	}


}
