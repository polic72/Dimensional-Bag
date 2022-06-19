package polic72.dimbag.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import polic72.dimbag.DimensionalBag;
import polic72.dimbag.core.ModEntities;


/**
 * Represents the rift that forms when 2 bags get a lil too friendly.
 * 
 * @author polic72
 */
public class RiftEntity extends Entity
{
	/**
	 * The name of the tag storing the tick counter for this Rift's NBT data.
	 */
	public static final String TICK_TAG = "tick";
	
	
	/**
	 * The value that the tick counter starts at.
	 */
	public static final int START_TICK_COUNTER = 100;
	
	
	/**
	 * The counter for how long the rift has left to live. It's best not to tell Rifts they have this, it scares them.
	 */
	private static final EntityDataAccessor<Integer> TICK_COUNTER = SynchedEntityData.defineId(RiftEntity.class, 
			EntityDataSerializers.INT);
	
	
	
	
	public RiftEntity(EntityType<? extends RiftEntity> entityType, Level level)
	{
		super(entityType, level);
	}
	
	
	public RiftEntity(Level level, int tick, double x, double y, double z)
	{
		this(ModEntities.RIFT.get(), level);
		
		setPos(x, y, z);
		
		entityData.set(TICK_COUNTER, tick);
	}
	
	
	/**
	 * Gets the number of ticks this Rift has left to live.
	 * 
	 * @return The number of ticks this Rift has left to live.
	 */
	public int getTick()
	{
		return entityData.get(TICK_COUNTER);
	}
	
	
	/**
	 * Sets the number of ticks this Rift has left to live.
	 * 
	 * @param tick The number of ticks this Rift has left to live.
	 */
	public void setTick(int tick)
	{
		entityData.set(TICK_COUNTER, tick);
	}
	
	
	/**
	 * Gets the width of the Rift in pixels.
	 * 
	 * @return The width of the Rift in pixels.
	 */
	public int getWidthPixels()
	{
		return 16;
	}
	
	
	/**
	 * Gets the height of the Rift in pixels.
	 * 
	 * @return The height of the Rift in pixels.
	 */
	public int getHeightPixels()
	{
		return 32;
	}
	
	
	@Override
	protected void defineSynchedData()
	{
		entityData.define(TICK_COUNTER, 0);
	}
	
	
	@Override
	protected void readAdditionalSaveData(CompoundTag nbt)
	{
		setTick(nbt.getInt(TICK_TAG));
	}
	
	
	@Override
	protected void addAdditionalSaveData(CompoundTag nbt)
	{
		nbt.putInt(TICK_TAG, getTick());
	}
	
	
	@Override
	public Packet<?> getAddEntityPacket()
	{
		return new ClientboundAddEntityPacket(this, 5);
	}
	
	
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet)
	{
		super.recreateFromPacket(packet);
		
		int test = packet.getData();
		
		DimensionalBag.LOGGER.info(String.valueOf(test));
	}
}
