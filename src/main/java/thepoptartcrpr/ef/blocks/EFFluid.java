package thepoptartcrpr.ef.blocks;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.init.EFBlocks;

public class EFFluid {
	
	public static Fluid fuel = new Fluid("fluid_fuel", new ResourceLocation(Variables.MODID + ":blocks/fluid_fuel_still"), new ResourceLocation(Variables.MODID + ":blocks/fluid_fuel_flowing")).setBlock(EFBlocks.fuel);

	public static void init() {
		FluidRegistry.registerFluid(fuel);
	}
	
}
