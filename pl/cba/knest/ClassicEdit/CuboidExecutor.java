package pl.cba.knest.ClassicEdit;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CuboidExecutor extends Executor{

	public void execute(Player p, List<String> args){
		Filling f = null;
		
		
		boolean dropmode = p.getGameMode()!=GameMode.CREATIVE;// && !p.hasPermission("ClassicEdit.nodrop");
		if(args.size()==1){
			try{
				f = Filling.parse(args.get(0));
			}catch(Exception e){
				p.sendMessage(ChatColor.RED+"Bad block name");
				return;
			}
		}
		CuboidCreation c = new CuboidCreation(p.getName());
		c.setFilling(f);
		if(dropmode) c.setDropmode(dropmode);
		Selector sel = new DoubleSelector(p, c);
		
		p.sendMessage(sel.getMessage((byte) 0));
		ClassicEdit.getCuboidManager().setSelector(p, sel);
	}


}
