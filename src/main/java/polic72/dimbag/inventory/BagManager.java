package polic72.dimbag.inventory;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.server.ServerLifecycleHooks;
import polic72.dimbag.Reference;


/**
 * The manager of all the bags in a given world.
 * <p/>
 * Heavily inspired by the
 * <a href="https://www.curseforge.com/minecraft/mc-mods/simply-backpacks">
 * Simply Backpacks mod</a> implementation of backpacks.
 * 
 * @author polic72
 */
public class BagManager extends SavedData
{
	private final HashMap<UUID, IItemHandler> data;
	
	
	private BagManager()
	{
		data = new HashMap<>();
	}
	
	
	/**
	 * Gets the {@link BagManager} hosted by the running world.
	 * 
	 * @return
	 */
	public static BagManager get()
	{
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
		{
			// Using the Overworld because it can persist data for all levels.
			return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage()
					.computeIfAbsent(BagManager::load, BagManager::new, Reference.MOD_ID + "_bag_data");
		}
		else
		{
			return null;
			// return new BagManager(); //This is the example in SB's code, not sure why.
		}
	}
	
	
	/**
	 * Loads a {@link BagManager} from the given <i>compoundTag</i> and returns it.
	 * 
	 * @param compoundTag The {@link CompoundTag} to get the {@link BagManager} from.
	 * @return The {@link BagManager} in the NBT data.
	 */
	public static BagManager load(CompoundTag compoundTag)
	{
		BagManager manager = new BagManager();
		
		if (compoundTag.contains("Bags"))
		{
//			manager.
			// Consider making all of the data static, like the example, to avoid creating a
			// new HashMap every time
			// get() is called.
		}
		
		return manager;
	}
	
	
	@Override
	public CompoundTag save(CompoundTag compoundTag)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
