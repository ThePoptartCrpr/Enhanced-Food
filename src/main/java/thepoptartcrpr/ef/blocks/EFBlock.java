package thepoptartcrpr.ef.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFItems;

public class EFBlock extends Block {
	
	public EFBlock(String unlocalizedName, String registryName, Material material, float hardness, float resistance) {
		super(material);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Variables.MODID, registryName));
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return this == EFBlocks.saltOre ? EFItems.salt : Item.getItemFromBlock(this);
	}
	
	public int quantityDropped(Random random) {
		return this == EFBlocks.saltOre ? 2 + random.nextInt(3) : 1;
	}

}
