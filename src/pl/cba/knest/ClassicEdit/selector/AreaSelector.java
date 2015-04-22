package pl.cba.knest.ClassicEdit.selector;



import org.bukkit.Location;

public abstract class AreaSelector extends Selector {
	public abstract Location getLocationMin();
	public abstract Location getLocationMax();

}
