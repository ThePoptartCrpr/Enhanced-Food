package thepoptartcrpr.ef.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;

public class EFItemBlock extends ItemBlock {
	
	public EFItemBlock(Block block, String unlocalizedName, String registryName) {
		super(block);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
	}

}
