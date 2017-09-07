package thepoptartcrpr.ef.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import thepoptartcrpr.ef.api.EFRecipeAPI;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.recipes.CuttingBoardRecipes;
import thepoptartcrpr.ef.recipes.OvenRecipes;

public class RecipeHandler {
	
	public static void removeRecipes() {
		
		// Crafting
		RecipeRemover.removeCrafting(Items.BREAD);
		
		// Smelting
		RecipeRemover.removeSmelting(new ItemStack(Items.COOKED_BEEF));
	}
	
	public static void registerRecipes() {
		
		// Vanilla machines
		registerCraftingRecipes();
		registerSmeltingRecipes();
		
		// Custom machines
		OvenRecipes.registerOvenRecipes();
		CuttingBoardRecipes.registerCuttingRecipes();
	}
	
	public static void registerCraftingRecipes() {
		// Shapeless
		GameRegistry.addShapelessRecipe(new ItemStack(EFItems.filletedTuna, 1), new Object[] { EFItems.fishTuna });
		GameRegistry.addShapelessRecipe(new ItemStack(EFItems.cutWood, 3), new Object[] { new ItemStack(EFItems.saw, 1, OreDictionary.WILDCARD_VALUE), Blocks.PLANKS });
		
		// Shaped
		GameRegistry.addRecipe(new ItemStack(EFItems.sawHandle), new Object[] { " SS", "S S", " S ", 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(EFItems.sawHead), new Object[] { "III", "  I", 'I', Items.IRON_INGOT });
		GameRegistry.addRecipe(new ItemStack(EFItems.saw), new Object[] { "AB", 'A', EFItems.sawHead, 'B', EFItems.sawHandle });
		GameRegistry.addRecipe(new ItemStack(EFItems.cuttingBoard), new Object[] { "CCC", 'C', EFItems.cutWood });
		GameRegistry.addRecipe(new ItemStack(EFItems.spoon), new Object[] { "  S", " S ", "W  ", 'S', Items.STICK, 'W', Blocks.PLANKS });
		GameRegistry.addRecipe(new ItemStack(EFItems.mixingBowl), new Object [] { "ISI", " I ", 'I', Items.IRON_INGOT, 'S', EFItems.spoon });
	}
	
	public static void registerSmeltingRecipes() {
	}
	
	private static void registerToolRecipe(Item ingot, Item pickaxe, Item axe, Item shovel, Item hoe, Item sword) {
		GameRegistry.addRecipe(new ItemStack(pickaxe), new Object[] { "III", " S ", " S ", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "II", "IS", " S", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "I", "S", "S", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "II", " S", " S", 'I', ingot, 'S', Items.STICK });
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "I", "I", "S", 'I', ingot, 'S', Items.STICK });
	}

}
