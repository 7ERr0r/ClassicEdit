package pl.cba.knest.ClassicEdit.Selectors;



import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.NotSelectedException;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.AreaCreation;

public abstract class AreaSelector extends Selector {
	public abstract Location getLocationMin() throws NotSelectedException;
	public abstract Location getLocationMax() throws NotSelectedException;
	public abstract void setCreation(AreaCreation c);
}
