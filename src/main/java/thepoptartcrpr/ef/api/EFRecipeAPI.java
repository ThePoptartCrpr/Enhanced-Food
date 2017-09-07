package thepoptartcrpr.ef.api;

import net.minecraft.item.ItemStack;
import thepoptartcrpr.ef.recipes.OvenRecipes;
import thepoptartcrpr.ef.utils.Utils;

public class EFRecipeAPI {
	
	/**
	 * Removes an oven recipe based on the given output ItemStack.
	 * IMPORTANT: DO NOT CALL THIS IN PREINIT. DOING SO CAN CRASH THE GAME.
	 * 
	 * @param output The output ItemStack for the recipe you wish to remove
	 */
	public static void removeOvenRecipe(ItemStack output) {
		for (int i = 0; i <= OvenRecipes.recipeList.size(); i++) {
			if (ItemStack.areItemsEqual((ItemStack) OvenRecipes.recipeList.get(i).output, output)) {
				OvenRecipes.recipeList.remove(i);
				Utils.getConsole().info("[API] Removed oven recipe for " + output.getDisplayName());
			}
		}
	}
	
	/**
	 * Adds an oven recipe to the game.
	 * IMPORTANT: DO NOT CALL THIS IN PREINIT. DOING SO CAN CRASH THE GAME.
	 * 
	 * @param input The ItemStack you wish the recipe to take as an input
	 * @param output The ItemStack you wish the recipe to output
	 */
	public static void addOvenRecipe(ItemStack input, ItemStack output) {
		OvenRecipes.addOvenRecipe(input, output);
		Utils.getConsole().info("[API] Added oven recipe for " + output.getDisplayName());
	}

}
