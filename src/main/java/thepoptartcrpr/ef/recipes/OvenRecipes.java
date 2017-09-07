package thepoptartcrpr.ef.recipes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.utils.Utils;

public class OvenRecipes {
	
	public final Object input;
	public final ItemStack output;
	
	public static ArrayList<OvenRecipes> recipeList = new ArrayList<OvenRecipes>();
	
	public static void registerOvenRecipes() {
		addOvenRecipe(new ItemStack(EFItems.breadDough), new ItemStack(Items.BREAD));
	}
	
	public OvenRecipes(Object input, ItemStack output) {
		this.output = output;
		this.input = input;
	}
	
	@Nullable
	public static ItemStack getOvenResult(ItemStack stack) {
		for (int i = 0; i < recipeList.size(); i++) {
			if (ItemStack.areItemsEqual((ItemStack) recipeList.get(i).input, stack)) {
				return (ItemStack) recipeList.get(i).output;
			}
		}
		return null;
	}
	
	private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public static void addOvenRecipe(Object input, ItemStack output) {
		ItemStack inputStack = (ItemStack) input;
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			OvenRecipes recipe = new OvenRecipes(inputStack, output);
			recipeList.add(recipe);
		}
	}
	
	public static void addOvenRecipe(Block input, Item output) {
		ItemStack inputStack = new ItemStack(Item.getItemFromBlock(input));
		ItemStack outputStack = new ItemStack(output);
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			outputStack = new ItemStack(output);
			OvenRecipes recipe = new OvenRecipes(inputStack, outputStack);
			recipeList.add(recipe);
		}
	}
	
	public static void addOvenRecipe(Item input, ItemStack output) {
		ItemStack inputStack = new ItemStack(input);
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			OvenRecipes recipe = new OvenRecipes(inputStack, output);
			recipeList.add(recipe);
		}
	}
	
	public static void addOvenRecipe(Block input, ItemStack output) {
		ItemStack inputStack = new ItemStack(Item.getItemFromBlock(input));
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			OvenRecipes recipe = new OvenRecipes(inputStack, output);
			recipeList.add(recipe);
		}
	}
	
}
