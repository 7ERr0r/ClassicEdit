package pl.cba.knest.ClassicEdit.selector;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import pl.cba.knest.ClassicEdit.creation.ICreation;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

public class WEAreaSelector extends AreaSelector {
	Location l1;
	Location l2;
	ICreation creation;
	@Override
	public Location getLocationA() {
		return l1;
	}

	@Override
	public Location getLocationB() {
		return l2;
	}


	
	@Override
	public Player getPlayer() {
		return creation.getPlayer();
	}

	@Override
	public boolean handleInteract(PlayerInteractEvent e) {
		return false;
	}

	@Override
	public boolean start(ICreation c) {
		this.creation = c;
		WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if(wep == null){
			msgPlayer(ChatColor.RED+"WorldEdit plugin is null");
			creation.stop();
			return false;
		}
		LocalPlayer lp = wep.wrapPlayer(getPlayer());
		LocalSession s = WorldEdit.getInstance().getSession(lp);
		Region r;
		try{
			r = s.getSelection(lp.getWorld());
		}catch(IncompleteRegionException e){
			msgPlayer(ChatColor.RED+"Select a region first");
			creation.stop();
			return false;
		}
		Vector min = r.getMinimumPoint();
		Vector max = r.getMaximumPoint();
		l1 = new Location(getPlayer().getWorld(), min.getBlockX(), min.getBlockY(), min.getBlockZ());
		l2 = new Location(getPlayer().getWorld(), max.getBlockX(), max.getBlockY(), max.getBlockZ());
		setFillingAuto();
		msgPlayer(ChatColor.LIGHT_PURPLE+"Selected with WorldEdit");
		return true;
	}

	@Override
	public void end() {
		
	}

	@Override
	public ICreation getCreation() {
		return creation;
	}



}
