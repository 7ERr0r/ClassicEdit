package pl.cba.knest.ClassicEdit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CuboidExecutor extends Executor{

	public void execute(Player p, String[] args){
		Filling f = null;
		if(args.length==1){
			try{
				f = Filling.parse(args[0]);
			}catch(Exception e){
				p.sendMessage(ChatColor.RED+"Bad block name");
				return;
			}
		}
		CuboidCreation c = new CuboidCreation(p.getName());
		c.setFilling(f);
		Selector sel = new DoubleSelector(p, c);
		
		p.sendMessage(sel.getMessage((byte) 0));
		ClassicEdit.getCuboidManager().setSelector(p, sel);
	}


}
