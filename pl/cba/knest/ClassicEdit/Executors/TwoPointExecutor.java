package pl.cba.knest.ClassicEdit.Executors;


import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Creations.TwoPointCreation;

public class TwoPointExecutor extends CreationExecutor {
	public TwoPointCreation getCreation(String nick){
		return new CuboidCreation(nick);
	}
	
	public void execute() throws ExecutorException{
		super.execute();
	}
}
