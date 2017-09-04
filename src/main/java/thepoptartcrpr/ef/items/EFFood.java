package thepoptartcrpr.ef.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thepoptartcrpr.ef.Variables;

public class EFFood extends ItemFood {

	private PotionEffect[] effects;
	
	public EFFood(String unlocalizedName, int amount, boolean isWolfFood, PotionEffect... potionEffects) {
		super(amount, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
		this.effects = potionEffects;
	}
	
	public EFFood(String unlocalizedName, int amount, float saturation, boolean isWolfFood, PotionEffect... potionEffects) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
		this.effects = potionEffects;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		for (PotionEffect effect : effects) {
			player.addPotionEffect(new PotionEffect(effect));
		}
	}

}
