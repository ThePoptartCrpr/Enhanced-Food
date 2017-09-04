package thepoptartcrpr.ef.blocks.crops;

import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.handlers.AchievementHandler;
import thepoptartcrpr.ef.init.EFItems;

public class BlockCornCrop extends BlockCrops {
	
	public BlockCornCrop(String unlocalizedName) {
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, unlocalizedName));
	}
	
	@Override
	protected Item getSeed() {
		return EFItems.cornSeeds;
	}
	
	@Override
	protected Item getCrop() {
		return EFItems.corn;
	}

}
