package polic72.dimbag.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import polic72.dimbag.DimensionalBag;


/**
 * The bag item class. Where the logic for using the item remains.
 * 
 * @author polic72
 */
public class BagItem extends Item
{
	public BagItem(Properties properties)
	{
		super(properties);
	}
	
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt)
	{
		return super.initCapabilities(stack, nbt);
	}
	
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
	{
		DimensionalBag.LOGGER.info("Bag used!");
		
		//
		
		return super.use(level, player, interactionHand);
	}
}
