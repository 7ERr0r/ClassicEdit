package pl.cba.knest.ClassicEdit.executor;



import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Session;


public class PauseExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException{
		super.execute();
		Session s = ClassicEdit.getCreationManager().getSession(player);

		if(s!=null){
			if(s.isPaused()){
				//msgPlayer(ChatColor.YELLOW+"Unpaused "+c.getName());
				s.unpause();
			}else{
				//msgPlayer(ChatColor.YELLOW+"Paused "+c.getName());
				s.pause();
			}
				
		}
		
	}

}
