package pl.cba.knest.ClassicEdit.Executors;


import org.bukkit.ChatColor;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.Selector;
import pl.cba.knest.ClassicEdit.Creations.CuboidCreation;
import pl.cba.knest.ClassicEdit.Selectors.TwoPointSelector;

public class CuboidExecutor extends TwoPointExecutor{
	public CuboidCreation getCreation(String nick){
		return new CuboidCreation(nick);
	}
	public void execute() throws ExecutorException{
		super.execute();
		
		// && !p.hasPermission("ClassicEdit.nodrop");
		if(params.size()==1){
			try{
				f = Filling.parse(params.get(0));
			}catch(Exception e){
				p.sendMessage(ChatColor.RED+"Bad block name");
				return;
			}
		}
		if(f != null && f.getMaterial() == null){
			p.sendMessage(ChatColor.RED+"Invalid block name or id");
			return;
		}
		CuboidCreation c = getCreation(p.getName());
		
		c.setFilling(f);
		
		c.setDashed(flags.contains("d"));
		//if(dropmode) c.setDropmode(dropmode);
		Selector sel = new TwoPointSelector(p, c);
		
		p.sendMessage(sel.getMessage((byte) 0));
		ClassicEdit.getCuboidManager().setSelector(p, sel);
	}


}
