package pl.cba.knest.ClassicEdit.executor;



import java.util.Iterator;






import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.creation.SleeperCreation;



public class SleeperExecutor extends PlayerCmdExecutor {

	public boolean loop = false;
	public void execute() throws ExecutorException{
		super.execute();
		int ticks;
		if(params.size()<1) throw new ExecutorException("Not enough parameters");
		try{
			ticks = Integer.parseInt(params.get(0));
		}catch(NumberFormatException e){
			throw new ExecutorException("Wrong number: sleep ticks");
		}
		SleeperCreation c = new SleeperCreation();
		c.setSleepTicks(ticks);
		c.setLoop(loop);
		c.attach(getSession());
	}
	@Override
	void flag(char c, Iterator<String> i) throws ExecutorException {
		switch(c){
		case 'l':
			loop = true;
		return;
		}
		super.flag(c, i);
	}
	public void help() {
		msgPlayer("Usage: /sleep <ticks>");
		
	}

}
