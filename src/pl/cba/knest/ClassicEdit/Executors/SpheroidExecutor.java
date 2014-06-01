package pl.cba.knest.ClassicEdit.Executors;

import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Creations.SpheroidCreation;



public class SpheroidExecutor extends CuboidExecutor{

	@Override
	public CuboidCreation getCreation(String nick) {
		return new SpheroidCreation(nick);
	}

	public void execute() throws ExecutorException{
		perms("ClassicEdit.create.spheroid");
		super.execute();
	}
}
