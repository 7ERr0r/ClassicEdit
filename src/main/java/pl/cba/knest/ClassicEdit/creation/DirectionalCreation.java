package pl.cba.knest.ClassicEdit.creation;

import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.DirectionSelector;


public abstract class DirectionalCreation extends Creation implements IClickableCreation {

	DirectionSelector directionSelector;
	
	public DirectionalCreation(DirectionSelector ds){
		this.directionSelector = ds;
		selectors.add(ds);
	}

	public boolean init(){
		return true;
	}
	@Override
	public void attach(Session s){
		super.attach(s);
	}

}
