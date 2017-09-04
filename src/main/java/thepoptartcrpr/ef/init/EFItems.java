package thepoptartcrpr.ef.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.items.EFFood;
import thepoptartcrpr.ef.items.EFItem;
import thepoptartcrpr.ef.items.EFItemBlock;
import thepoptartcrpr.ef.items.tools.EFGenericTool;

public class EFItems {
	
	// Ingredients
	public static Item salt;
	public static Item filletedTuna;
	public static Item breadDough;
	
	// Seeds
	public static Item cornSeeds;
	
	// Crop food
	public static Item corn;
	
	// Fish
	public static Item fishTuna;
	
	// Tools
	public static Item saw;
	public static Item cuttingBoard;
	public static Item mixingBowl;
	public static Item spoon;
	
	// Parts
	public static Item sawHead;
	public static Item sawHandle;
	public static Item cutWood;
	
	// Buckets
//	public static ItemBlock bucketFuel;
	
	public static void init() {
		
		// Ingredients
		salt = new EFItem("salt", "salt");
		filletedTuna = new EFItem("filleted_tuna", "filleted_tuna");
		breadDough = new EFItem("dough_bread", "dough_bread");
		
		// Seeds
		cornSeeds = new ItemSeeds(EFBlocks.cornCrop, Blocks.FARMLAND).setUnlocalizedName("corn_seeds").setRegistryName(new ResourceLocation(Variables.MODID, "corn_seeds"));
		
		// Crop food
		corn = new EFFood("corn", 2, 2, false);
		
		// Fish
		fishTuna = new EFItem("tuna_fish", "tuna_fish");
		
		// Tools
		saw = new EFGenericTool("saw", "saw");
		cuttingBoard = new EFGenericTool("cutting_board", "cutting_board");
		mixingBowl = new EFItem("mixing_bowl", "mixing_bowl");
		spoon = new EFItem("spoon", "spoon");
		
		// Parts
		sawHead = new EFItem("saw_head", "saw_head");
		sawHandle = new EFItem("saw_handle", "saw_handle");
		cutWood = new EFItem("cut_wood", "cut_wood");
		
		// Buckets
//		bucketFuel = new EFItemBlock(EFBlocks.fuel, "bucket_fuel", "bucket_fuel");
		
	}
	
	public static void register() {
		// Ingredients
		registerItem(salt, EnhancedFood.ingredients);
		registerItem(filletedTuna, EnhancedFood.ingredients);
		registerItem(breadDough, EnhancedFood.ingredients);
		
		// Seeds
		registerItem(cornSeeds, EnhancedFood.crops);
		
		// Crop food
		registerItem(corn, EnhancedFood.crops);
		
		// Fish
		registerItem(fishTuna, EnhancedFood.ingredients);
		
		// Tools
		registerItem(saw, EnhancedFood.tools);
		registerItem(cuttingBoard, EnhancedFood.tools);
		registerItem(mixingBowl, EnhancedFood.tools);
		registerItem(spoon, EnhancedFood.tools);
		
		// Parts
		registerItem(sawHead, EnhancedFood.parts);
		registerItem(sawHandle, EnhancedFood.parts);
		registerItem(cutWood, EnhancedFood.parts);
		
		// Buckets
//		registerItem(bucketFuel, CreativeTabs.MATERIALS);
		
	}
	
	public static void registerRenders() {
		// Ingredients
		registerRender(salt);
		registerRender(filletedTuna);
		registerRender(breadDough);
		
		// Seeds
		registerRender(cornSeeds);
		
		// Crop food
		registerRender(corn);
		
		// Fish
		registerRender(fishTuna);
		
		// Tools
		registerRender(saw);
		registerRender(cuttingBoard);
		registerRender(mixingBowl);
		registerRender(spoon);
		
		// Parts
		registerRender(sawHead);
		registerRender(sawHandle);
		registerRender(cutWood);
		
		// Buckets
//		registerRender(bucketFuel);
		
	}
	
	public static void registerItem(Item item) {
		GameRegistry.register(item);
	}
	
	public static void registerItem(Item item, CreativeTabs tab) {
		item.setCreativeTab(tab);
		GameRegistry.register(item);
	}
	
	public static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Variables.MODID, item.getUnlocalizedName().substring(5)), "inventory"));
	}

}
