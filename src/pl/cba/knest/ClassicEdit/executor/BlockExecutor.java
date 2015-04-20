package pl.cba.knest.ClassicEdit.executor;



import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import pl.cba.knest.ClassicEdit.ClassicEdit;
import pl.cba.knest.ClassicEdit.Creation;
import pl.cba.knest.ClassicEdit.ExecutorException;
import pl.cba.knest.ClassicEdit.Filling;
import pl.cba.knest.ClassicEdit.creation.FilledCreation;


public class BlockExecutor extends PlayerCmdExecutor {
	public void execute() throws ExecutorException{
		super.execute();
		Creation c = ClassicEdit.getCreationManager().getCreation(player);
		if(c == null){
			throw new ExecutorException(ChatColor.RED+"You don't have any cuboid running");
		}
		if(c instanceof FilledCreation){
			Filling f;
			if(params.size()>0){
				f = Filling.parse(params.get(0));
			}else{
				ItemStack is = player.getItemInHand();
				if(is != null && is.getType().isBlock()){
					f = new Filling(is.getType(), (byte) 0);
				}else{
					f = new Filling(Material.AIR, (byte) 0);
				}
				
			}
			FilledCreation fc = (FilledCreation) c;
			fc.setFilling(f);
			msgPlayer(ChatColor.YELLOW+"Filling set to "+f.toString());
		}else{
			throw new ExecutorException(ChatColor.RED+"Your creation can't have filling");
		}
	}

}
