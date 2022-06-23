package polic72.dimbag.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;
import polic72.dimbag.DimensionalBag;
import polic72.dimbag.core.ModSounds;


/**
 * Represents the rift that forms when 2 bags get a lil too friendly.
 * 
 * @author polic72
 */
public class RiftEntity extends Entity
{
	/**
	 * The value that the tick counter starts at.
	 */
	public static final int START_TICK_COUNTER = 100;
	
	
	/**
	 * The block radius of the sphere of influence of any given rift.
	 */
	public static final double RADIUS = 10;
	
	
	/**
	 * The name of the tag storing the tick counter for this Rift's NBT data.
	 */
	protected static final String TICK_TAG = "tick";
	
	
	/**
	 * The volume of the sounds played by the rift.
	 */
	protected static final float VOLUME = 5F;
	
	
	/**
	 * The constant at which entities get pulled in to the rift.
	 */
	protected static final double VELOCITY_CONSTANT = 1;
	
	
	/**
	 * The counter for how long the rift has left to live. It's best not to tell Rifts they have this, it scares them.
	 */
	protected static final EntityDataAccessor<Integer> TICK_COUNTER = SynchedEntityData.defineId(RiftEntity.class, 
			EntityDataSerializers.INT);
	
	
	
	
	/**
	 * The cube to check for entities to move in.
	 */
	private AABB cubeOfInfluence;
	
	
	/**
	 * The point to pull entities in to.
	 */
	protected Vec3 pullCenter;
	
	
	
	
	public RiftEntity(EntityType<? extends RiftEntity> entityType, Level level)
	{
		super(entityType, level);
	}
	
	
//	public RiftEntity(EntityType<? extends RiftEntity> entityType, Level level, int tick, double x, double y, double z)
//	{
//		this(entityType, level);
//		
//		setPos(x, y, z);
//		
//		entityData.set(TICK_COUNTER, tick);
//	}
//	
//	
//	public RiftEntity(EntityType<? extends RiftEntity> entityType, Level level, double x, double y, double z)
//	{
//		this(entityType, level, START_TICK_COUNTER, x, y, z);
//	}
	
	
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
	
	
	@Override
	protected void defineSynchedData()
	{
		entityData.define(TICK_COUNTER, 0);
	}
	
	
	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		
		
		playSound(ModSounds.OPEN_RIFT.get(), VOLUME, 1F);
		
		setTick(START_TICK_COUNTER);
		
		
		cubeOfInfluence = new AABB(getX() - RADIUS, getY() - RADIUS, getZ() - RADIUS, 
				getX() + RADIUS, getY() + RADIUS, getZ() + RADIUS);
		
		
		pullCenter = getBoundingBox().getCenter();
	}
	
	
	@Override
	public void tick()
	{
		super.tick();
		
		
		if (!level.isClientSide)
		{
			if (getTick() <= 0)
			{
				kill();
				
				playSound(SoundEvents.CHICKEN_EGG, START_TICK_COUNTER, 1F);
			}
			else
			{
				setTick(getTick() - 1);
				
				
				for (Entity entity : level.getEntities(this, getBoundingBox()))
				{
					if (wouldInteract(entity))
					{
						//Teleport entities.
					}
				}
				
				
				//
			}
		}
	}
	
	
	/**
	 * Tells whether or not the given entity will interact with rifts.
	 * <p/>
	 * By default Creative players, Spectator players, and flying players are immune.
	 * 
	 * @param entity The entity to check.
	 * @return True when rifts can interact with the given entity. False otherwise.
	 */
	private static boolean wouldInteract(Entity entity)
	{
		if (entity.level.isClientSide)
		{
			return false;
		}
		
		
		if (entity instanceof RiftEntity)
		{
			return false;
		}
		
		
		if (entity instanceof Player player)
		{
			return !player.getAbilities().flying && !player.isCreative() && !player.isSpectator();
		}
		
		
		return true;
	}
	
	
	/**
	 * Checks the given entity for exceptions and applies velocity towards the rift if permitted.
	 * <p/>
	 * Uses the {@link RiftEntity#wouldInteract(Entity)} method to check.
	 * 
	 * @param entity The entity to check/apply velocity to.
	 */
	private void checkApplyVelocity(Entity entity)
	{
		if (!level.isClientSide)
		{
			if (wouldInteract(entity))
			{
				pullEntity(entity);
			}
		}
	}
	
	
	/**
	 * Pulls the given entity towards this rift.
	 * 
	 * @param entity The entity to pull towards the rift.
	 */
	protected void pullEntity(Entity entity)
	{
		if (!level.isClientSide)
		{
			Vec3 entityCenter = entity.getBoundingBox().getCenter();
			
			double distance = distance(entityCenter);
			
			if (distance < 1)
			{
				entity.push(0, 1, 0);
			}
			else
			{
				distance *= distance;
				
				double x = -(VELOCITY_CONSTANT * (pullCenter.x - entityCenter.x)) / distance;
				
				double y = -(VELOCITY_CONSTANT * (pullCenter.y - entityCenter.y)) / distance;
				
				double z = -(VELOCITY_CONSTANT * (pullCenter.z - entityCenter.z)) / distance;
				
				
				entity.push(x, y, z);
			}
		}
	}
	
	
	/**
	 * Gets the distance of the given entity's center from this rift's pull center.
	 * 
	 * @param entityCenter The center of the entity's bounding box.
	 * @return The distance of the given entity's center to this rift's pull center.
	 */
	private double distance(Vec3 entityCenter)
	{
		return Math.sqrt(Math.pow(pullCenter.x - entityCenter.x, 2) + Math.pow(pullCenter.y - entityCenter.y, 2)
			+ Math.pow(pullCenter.z - entityCenter.z, 2));
	}
	
	
	protected boolean inSphereOfInfluence(Vec3 entityCenter)
	{
		//TODO Check if in a sphere centered at the pullCenter.
		
		return false;
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
		return new ClientboundAddEntityPacket(this, getTick());
	}
	
	
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet)
	{
		super.recreateFromPacket(packet);
		
		setTick(packet.getData());
	}
}
