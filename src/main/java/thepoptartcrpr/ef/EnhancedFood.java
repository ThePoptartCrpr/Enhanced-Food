package thepoptartcrpr.ef;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thepoptartcrpr.ef.blocks.EFFluid;
import thepoptartcrpr.ef.creativetabs.TabEFCrops;
import thepoptartcrpr.ef.creativetabs.TabEFIngredient;
import thepoptartcrpr.ef.creativetabs.TabEFMachines;
import thepoptartcrpr.ef.creativetabs.TabEFOres;
import thepoptartcrpr.ef.creativetabs.TabEFParts;
import thepoptartcrpr.ef.creativetabs.TabEFTools;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.handlers.AchievementHandler;
import thepoptartcrpr.ef.handlers.GrassHandler;
import thepoptartcrpr.ef.handlers.RecipeHandler;
import thepoptartcrpr.ef.handlers.RecipeRemover;
import thepoptartcrpr.ef.init.EFBlocks;
import thepoptartcrpr.ef.init.EFCapabilities;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.init.EFTools;
import thepoptartcrpr.ef.proxy.CommonProxy;
import thepoptartcrpr.ef.recipes.CuttingBoardRecipes;
import thepoptartcrpr.ef.recipes.OvenRecipes;

@Mod(modid = Variables.MODID, name = Variables.NAME, version = Variables.VERSION)
public class EnhancedFood {
	
	public static final CreativeTabs ingredients = new TabEFIngredient();
	public static final CreativeTabs ores = new TabEFOres();
	public static final CreativeTabs crops = new TabEFCrops();
	public static final CreativeTabs machines = new TabEFMachines();
	public static final CreativeTabs tools = new TabEFTools();
	public static final CreativeTabs parts = new TabEFParts();
	
	thepoptartcrpr.ef.handlers.EventHandler eventHandler = new thepoptartcrpr.ef.handlers.EventHandler();
	
	@Mod.Instance(Variables.MODID)
	public static EnhancedFood instance;
	
	@SidedProxy(serverSide = Variables.SERVER_PROXY_CLASS, clientSide = Variables.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	
	static {
		FluidRegistry.enableUniversalBucket();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EFFluid.init();
		EFBlocks.init();
		EFItems.init();
		EFTools.init();
		
		EFBlocks.register();
		EFItems.register();
		EFTools.register();
		
		FluidRegistry.addBucketForFluid(EFFluid.fuel);
		
		proxy.preInit();
		proxy.registerRenders();
		proxy.registerTileEntities();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(EnhancedFood.instance, new GuiHandler());
		EFCapabilities.registerCapabilities();
		
		AchievementHandler.registerAchievements();
		
		RecipeRemover.removeCrafting(Items.BREAD);
		RecipeRemover.removeSmelting(new ItemStack(Items.COOKED_BEEF));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		
		eventHandler.registerEvents();
		
		RecipeHandler.registerCraftingRecipes();
		RecipeHandler.registerSmeltingRecipes();
		
		OvenRecipes.registerOvenRecipes();
		CuttingBoardRecipes.registerCuttingRecipes();
		
		GrassHandler.registerSeeds();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
