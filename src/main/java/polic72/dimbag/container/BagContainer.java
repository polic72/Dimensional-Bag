package polic72.dimbag.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import polic72.dimbag.core.ModContainers;


public class BagContainer extends AbstractContainerMenu
{
	
	public BagContainer(int windowID)
	{
		super(ModContainers.BAG_CONTAINER.get(), windowID);
	}
	
	
	@Override
	public boolean stillValid(Player player)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
