package polic72.dimbag.util;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;


/**
 * Represents a helper for Rift teleportation.
 * 
 * @author polic72
 */
public class TeleportHelper
{
	/**
	 * The most the {@link TeleportHelper} will try to look for a good teleportation location.
	 * @see TeleportHelper#pickDimPos(MinecraftServer)
	 */
	private static final int PRECAUTION = 150;
	
	
	/**
	 * The multiplier for the coordinate in the X/Z plane that entities will be teleported to.
	 * <p/>
	 * This number is multiplied by a random number using the normal distribution with a mean of 0 with a standard deviation 
	 * of 1.
	 */
	private static final double COORDINATE_MULTIPLIER = 8000;
	
	
	/**
	 * Set of blocks that are deemed dangerous to teleport to, even if a full block.
	 */
	private static final HashSet<ResourceLocation> DANGER_BLOCKS = new HashSet<>();
	
	
	/**
	 * Picks a dimension and location to teleport a player to. Any registered dimension is fair game, and any "safe" location 
	 * is too.
	 * <p/>
	 * This method is the "slow" variety, meaning it takes no care into what chunks have already been generated or not. It 
	 * will choose a random dimension and location, load the chunks (generating if need be), and check for safety until it 
	 * finds one.
	 * 
	 * @param server The server to get the dimensions and position from.
	 * @return A {@link Pair} containing the {@link Level} (dimension) and {@link BlockPos} to teleport a player to. Null if 
	 * no position could be found before hitting the {@link TeleportHelper#PRECAUTION} limit.
	 * 
	 * @see TeleportHelper#isSafeLocation(Level, BlockPos)
	 * @see TeleportHelper#pickDimPos_Fast(MinecraftServer)
	 */
	public static Pair<Level, BlockPos> pickDimPos_Slow(MinecraftServer server)
	{
		@SuppressWarnings("unchecked")
		ResourceKey<Level>[] levelKeys = server.levelKeys().toArray(new ResourceKey[0]);
		
		
		Random random = new Random();
		
		for (int i = 0; i < PRECAUTION; ++i)
		{
			ResourceKey<Level> chosenKey = levelKeys[random.nextInt(levelKeys.length)];
			
			ServerLevel level = server.getLevel(chosenKey);
			
			int x = (int)(random.nextGaussian() * COORDINATE_MULTIPLIER);
			int z = (int)(random.nextGaussian() * COORDINATE_MULTIPLIER);
			
			int y = random.nextInt(level.getMinBuildHeight(), level.getMaxBuildHeight());
			
			
			BlockPos sendingLocation = scanSafeLocation(level, new BlockPos(x, y, z));
			
			if (sendingLocation != null)
			{
				return new Pair<Level, BlockPos>(level, sendingLocation.immutable());
			}
		}
		
		
		return null;
	}
	
	
	/**
	 * Picks a dimension and location to teleport a player to. Any registered dimension is fair game, and any "safe" location 
	 * is too.
	 * <p/>
	 * This method is the "fast" variety, meaning it takes care to <b>not<b/> generate chunks when searching for a location. 
	 * It will choose a random dimension and location, load the chunks (if they have already been generated), and check for 
	 * safety until it finds one. This means that dimension that have not been generated at all are essentially off the 
	 * table.
	 * 
	 * @param server The server to get the dimensions and position from.
	 * @return A {@link Pair} containing the {@link Level} (dimension) and {@link BlockPos} to teleport a player to. Null if 
	 * no position could be found before hitting the {@link TeleportHelper#PRECAUTION} limit.
	 * 
	 * @see TeleportHelper#isSafeLocation(Level, BlockPos)
	 * @see TeleportHelper#pickDimPos_Fast(MinecraftServer)
	 */
	public static Pair<Level, BlockPos> pickDimPos_Fast(MinecraftServer server)
	{
		//I do not know how to check if a chunk is generated or not, yet.
		
//		@SuppressWarnings("unchecked")
//		ResourceKey<Level>[] levelKeys = server.levelKeys().toArray(new ResourceKey[0]);
//		
//		
//		Random random = new Random();
//		
//		for (int i = 0; i < PRECAUTION; ++i)
//		{
//			ResourceKey<Level> chosenKey = levelKeys[random.nextInt(levelKeys.length)];
//			
//			ServerLevel level = server.getLevel(chosenKey);
//			
//			level.load
//			
//			int x = (int)(random.nextGaussian() * COORDINATE_MULTIPLIER);
//			int z = (int)(random.nextGaussian() * COORDINATE_MULTIPLIER);
//			
//			int y = random.nextInt(level.getMinBuildHeight(), level.getMaxBuildHeight());
//			
//			
//			BlockPos sendingLocation = scanSafeLocation(level, new BlockPos(x, y, z));
//			
//			if (sendingLocation != null)
//			{
//				return new Pair<Level, BlockPos>(level, sendingLocation);
//			}
//		}
		
		
		return null;
	}
	
	
	/**
	 * Gets a safe {@link BlockPos} from the given <i>level</i> and <i>pos</i>.
	 * <p/>
	 * Starts at the given <i>pos</i>, looks up on the y coordinate until world height is met, loops to the bottom, then 
	 * back up to <i>pos</i>. If any safe locations are found, it will be returned.
	 * <p/>
	 * <b>This method will load in the chunk specified to test for safety, generating it if need be.</b>
	 * 
	 * @param level The level to check for a safe location from.
	 * @param pos The initial position to look for a safe location from.
	 * @return The {@link BlockPos} that is safe from the given <i>pos</i>. Null if no safe location could be found.
	 * 
	 * @see TeleportHelper#isSafeLocation(Level, BlockPos)
	 */
	public static BlockPos scanSafeLocation(Level level, BlockPos pos)
	{
		int maxHeight = level.getMaxBuildHeight();
		int minHeight = level.getMinBuildHeight();
		
		BlockPos workingPos = null;
		
		
		for (int i = pos.getY(); i < maxHeight; ++i)
		{
			workingPos = new BlockPos(pos.getX(), i, pos.getZ());
			
			if (isSafeLocation(level, workingPos))
			{
				return workingPos;
			}
		}
		
		
		for (int i = minHeight; i < pos.getY(); ++i)
		{
			workingPos = new BlockPos(pos.getX(), i, pos.getZ());
			
			if (isSafeLocation(level, workingPos))
			{
				return workingPos;
			}
		}
		
		
		return null;
	}
	
	
	/**
	 * Checks if the given <i>pos</i> in the given <i>level</i> is a safe location to teleport to.
	 * <p/>
	 * "Safe" means that there is air at, a full-block directly underneath, and air directly above the given <i>pos</i>. 
	 * Any blocks inside the internal "danger blocks" determined in the config are avoided during teleportation.This is 
	 * safe enough for players and most other mobs to not immediately die, but it doesn't necessarily guarantee a fun time 
	 * immediately after...
	 * <p/>
	 * <b>This method will load in the chunk specified to test for safety, generating it if need be.</b>
	 * 
	 * @param level The level to check.
	 * @param pos The block position to check.
	 * @return True if the given <i>pos</i> in the given <i>level</i> is a safe location to teleport to. False otherwise. 
	 * Always false on the client logical side.
	 */
	public static boolean isSafeLocation(Level level, BlockPos pos)
	{
		if (level.isClientSide)
		{
			return false;
		}
		
		
		if (DANGER_BLOCKS.contains(level.getBlockState(pos.below()).getBlock().getRegistryName()))
		{
			return false;
		}
		
		
		if (Block.isFaceFull(level.getBlockState(pos.below()).getCollisionShape(level, pos.below()), Direction.UP))
		{
			return level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir();
		}
		
		
		return false;
	}
	
	
	/**
	 * Tries to add the given registry name of a block to the internal danger blocks.
	 * 
	 * @param blockRegistryName The registry name of a block.
	 * @return True if the block was successfully added to the danger blocks. False otherwise.
	 */
	public static boolean addDangerBlock(ResourceLocation blockRegistryName)
	{
		return DANGER_BLOCKS.add(blockRegistryName);
	}
}
