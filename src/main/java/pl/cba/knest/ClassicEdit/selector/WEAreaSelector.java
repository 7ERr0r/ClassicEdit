package pl.cba.knest.ClassicEdit.selector;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import pl.cba.knest.ClassicEdit.creation.ICreation;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class WEAreaSelector extends AreaSelector {





	@Override
	public boolean start(ICreation c) {
		this.creation = c;
		WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(wep == null){
			msgPlayer(ChatColor.RED+"WorldEdit plugin is null");
			creation.stop();
			return false;
		}
		Selection s = wep.getSelection(getPlayer());
		if(s == null){
			msgPlayer(ChatColor.RED+"Select a region first");
			creation.stop();
			return false;
		}
		Location min = s.getMinimumPoint();
		Location max = s.getMaximumPoint();
		l1 = new Location(getPlayer().getWorld(), min.getBlockX(), min.getBlockY(), min.getBlockZ());
		l2 = new Location(getPlayer().getWorld(), max.getBlockX(), max.getBlockY(), max.getBlockZ());
		setFillingAuto();
		msgPlayer(ChatColor.LIGHT_PURPLE+"Selected with WorldEdit");
		return true;
	}

	@Override
	public void end() {
		
	}




}
