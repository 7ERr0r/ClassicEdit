package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.Session;
import pl.cba.knest.ClassicEdit.Selectors.DirectionSelector;


public abstract class ClickableCreation extends FilledCreation {

	DirectionSelector directionSelector;
	


	public abstract void click(Location location);
	public void init(){
		
	}
	public void setDirectionSelector(DirectionSelector s) {
		this.directionSelector = s;
		s.setCreation(this);
	}
	@Override
	public void attach(Session s){
		super.attach(s);
		selectors.add(directionSelector);
	}

}
