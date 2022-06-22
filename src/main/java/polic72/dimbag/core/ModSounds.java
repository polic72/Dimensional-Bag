package polic72.dimbag.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import polic72.dimbag.Reference;


/**
 * A storage hub for all of the sounds in the mod.
 * 
 * @author polic72
 */
public class ModSounds
{
	/**
	 * The main resister for the sounds of the mod.
	 */
	public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			Reference.MOD_ID);
	
	
	public static final RegistryObject<SoundEvent> OPEN_RIFT = REGISTER.register("open_rift", 
			() -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, "open_rift")));
}
