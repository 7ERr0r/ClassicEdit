package pl.cba.knest.ClassicEdit.Selectors;



import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.AreaCreation;

public abstract class AreaSelector extends Selector {
	public abstract Location getLocationMin();
	public abstract Location getLocationMax();
	public abstract void setCreation(AreaCreation c);
}
