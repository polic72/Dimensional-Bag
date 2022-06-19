package polic72.dimbag.core;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import polic72.dimbag.Reference;
import polic72.dimbag.entity.RiftEntity;


/**
 * A storage hub for all of the entities in the mod.
 * 
 * @author polic72
 */
public class ModEntities
{
	/**
	 * The main resister for the entities of the mod.
	 */
	public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, 
		Reference.MOD_ID);
	
	
	/**
	 * The rift entity.
	 */
	public static final RegistryObject<EntityType<? extends RiftEntity>> RIFT = REGISTER.register("rift", 
		() -> EntityType.Builder.<RiftEntity>of(RiftEntity::new, MobCategory.MISC)
		.sized(1, 2).setShouldReceiveVelocityUpdates(false).fireImmune().build("rift"));
	
	
	//Another way to do it:
//	/**
//	 * The rift entity.
//	 */
//	public static final RegistryObject<EntityType<?>> RIFT = REGISTER.register("rift", 
//		() -> EntityType.Builder.of((EntityType.EntityFactory<RiftEntity>) 
//				(EntityType<RiftEntity> ent, Level level) -> new RiftEntity(level), 
//				MobCategory.MISC)
//		.sized(1, 2).setShouldReceiveVelocityUpdates(false).fireImmune().build("rift"));
}
