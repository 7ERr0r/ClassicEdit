package pl.cba.knest.ClassicEdit.creation;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockEvent;

public class SleeperCreation extends Creation {
	protected int sleepTicks;
	protected int countdown;
	protected boolean loop;
	@Override
	public void run() {
		this.countdown--;
		if(this.countdown<0){
			if(loop){
				init();
				rotate();
			}else{
				stop();
			}
		}
	}
	public void setSleepTicks(int sleepTicks){
		this.sleepTicks = sleepTicks;
	}
	@Override
	public String getName(){
		return "sleeper";
	}

	@Override
	public void onBlockPhysics(BlockEvent e){
		return;
	}

	@Override
	public boolean init(){
		this.countdown = sleepTicks;
		return true;
	}
	@Override
	public void msgStart(){
		boolean active = session.isActive(this);
		msgPlayer(ChatColor.YELLOW+(active?"Sleeping":"Queued sleep"));
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

}
