package pl.cba.knest.ClassicEdit.executor;



import com.mongodb.BasicDBObject;

import pl.cba.knest.ClassicEdit.Backend;
import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.creation.Creation;


public class ClassicEditExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException {
		super.execute();
		boolean save = params.contains("save");
		Backend b = null;
		if(save){
			b = ClassicEdit.getInstance().getBackend();
			b.getCreations().remove(new BasicDBObject());
		}
		
		msgPlayer("ClassicEdit v"+ClassicEdit.getInstance().getDescription().getVersion()+" by 7ERr0r");
		Session s = ClassicEdit.getCreationManager().getSession(player);
		msgPlayer("Pending creation: "+s.getPending());
		msgPlayer("Active creations:");
		for(Creation c : s.getCreations()){
			msgPlayer(c.toString());
			b.getCreations().insert(Creation.serializeCreation(c));
		}

	}

}
