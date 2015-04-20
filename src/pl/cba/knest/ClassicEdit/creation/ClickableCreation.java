package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.selector.DirectionSelector;


public abstract class ClickableCreation extends FilledCreation {

	DirectionSelector directionSelector;
	


	public abstract void click(Location location);
	public boolean init(){
		return true;
	}
	public void setDirectionSelector(DirectionSelector s) {
		this.directionSelector = s;
		s.setCreation(this);
	}
	@Override
	public void attach(Session s){
		selectors.add(directionSelector);
		super.attach(s);
	}

}
