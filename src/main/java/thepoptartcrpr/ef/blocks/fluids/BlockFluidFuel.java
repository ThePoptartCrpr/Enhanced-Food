package thepoptartcrpr.ef.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import thepoptartcrpr.ef.blocks.EFFluid;

public class BlockFluidFuel extends BlockFluidClassic {
	
	public BlockFluidFuel(String unlocalizedName, String registryName, Material material) {
		super(EFFluid.fuel, material);
		this.setCreativeTab(null);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
	}

}
