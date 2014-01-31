package pl.cba.knest.ClassicEdit;

import org.bukkit.entity.Player;

public class CuboidExecutor extends Executor{

	public void execute(Player p, String[] args){
		
		
		CuboidCreation c = new CuboidCreation(p.getName());
		
		Selector sel = new DoubleSelector(p, c);
		p.sendMessage(sel.getMessage((byte) 0));
		ClassicEdit.getCuboidManager().setSelector(p, sel);
	}


}
