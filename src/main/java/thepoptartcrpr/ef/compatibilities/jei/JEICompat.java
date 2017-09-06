package thepoptartcrpr.ef.compatibilities.jei;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategory;
import thepoptartcrpr.ef.compatibilities.jei.categories.oven.OvenRecipeCategory;
import thepoptartcrpr.ef.recipes.OvenRecipes;
import thepoptartcrpr.ef.utils.Utils;

@JEIPlugin
public class JEICompat implements IModPlugin {
	
	public static IJeiHelpers jeiHelpers;
	public static IModRegistry modRegistry;
	public static IDrawable slotDrawable;
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime runtime) {
		
	}

	@Override
	public void register(IModRegistry registry) {
		modRegistry = registry;
		jeiHelpers = modRegistry.getJeiHelpers();
		
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		slotDrawable = guiHelper.getSlotDrawable();
		EFRecipeCategory[] categories = {
				new OvenRecipeCategory(guiHelper)
		};
		modRegistry.addRecipeCategories(categories);
		modRegistry.addRecipeHandlers(categories);
		
		modRegistry.addRecipes(new ArrayList(OvenRecipes.recipeList));
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry registry) {
		
	}

}
