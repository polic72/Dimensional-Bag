package polic72.dimbag.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


/**
 * Represents the rift that forms when 2 bags get a lil too friendly.
 * 
 * @author polic72
 */
public class RiftEntity extends Entity
{
	public RiftEntity(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}
	
	
	@Override
	protected void defineSynchedData()
	{
		// TODO Auto-generated method stub
	}
	
	
	@Override
	protected void readAdditionalSaveData(CompoundTag nbt)
	{
		// TODO Auto-generated method stub
	}
	
	
	@Override
	protected void addAdditionalSaveData(CompoundTag nbt)
	{
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public Packet<?> getAddEntityPacket()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
