package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.Selectors.DirectionSelector;


public abstract class ClickableCreation extends FilledCreation{

	DirectionSelector directionSelector;
	
	public ClickableCreation(Session s) {
		super(s);
	}

	public abstract void click(Location location);
	public void init(){
		
	}
	public void setDirectionSelector(DirectionSelector s) {
		this.directionSelector = s;
		s.setCreation(this);
	}
	@Override
	public Selector[] getSelectors() {
		return new Selector[]{directionSelector};
	}

}
