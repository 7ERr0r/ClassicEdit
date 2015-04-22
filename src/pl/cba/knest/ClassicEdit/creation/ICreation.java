package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.entity.Player;

public interface ICreation {
	public Player getPlayer();
	public void start();
	public void stop();
	public void pause();
	public void unpause();
}
