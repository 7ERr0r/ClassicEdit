package pl.cba.knest.ClassicEdit.Creations;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockPhysicsEvent;

import pl.cba.knest.ClassicEdit.Creation;

public class GunCreation extends Creation{

	public GunCreation(String nick) {
		super(nick);
	}

	@Override
	public void run() {
		
		
	}

	@Override
	public String getName() {
		return "gun";
	}



	@Override
	public boolean start(){
		msgPlayer(ChatColor.GREEN+"Gun active");
		return true;
	}

	@Override
	public int getTaskid() {
		return 0;
	}

	@Override
	public void setTaskid(int taskid) {
		
	}

	@Override
	public void onBlockPhysics(BlockPhysicsEvent e){
		
	}
	
}
