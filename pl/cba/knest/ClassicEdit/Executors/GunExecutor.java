package pl.cba.knest.ClassicEdit.Executors;


import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.GunCreation;
import pl.cba.knest.ClassicEdit.Creations.InfiniteCreation;
import pl.cba.knest.ClassicEdit.Selectors.InfiniteSelector;

public class GunExecutor extends CreationExecutor {

	

	
	public void execute() throws ExecutorException{
		super.execute();
		p.sendMessage("(gun)");
		InfiniteCreation c = new GunCreation(p.getName());
		Selector s = new InfiniteSelector(p, c);
		
		ClassicEdit.getCuboidManager().setSelector(p, s);
		s.start();
	}
}
