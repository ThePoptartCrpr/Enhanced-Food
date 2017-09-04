package thepoptartcrpr.ef.compatibilities.jei.categories.oven;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import thepoptartcrpr.ef.recipes.OvenRecipes;

public class OvenRecipeWrapper extends BlankRecipeWrapper {
	
	private final List<ItemStack> inputs;
	private final ItemStack output;
	
	public OvenRecipeWrapper(OvenRecipes recipe) {
		this.inputs = (List<ItemStack>) (recipe.input instanceof List ? recipe.input : Arrays.asList((ItemStack) recipe.input));
		this.output = recipe.output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(@Nonnull Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		
	}

}
