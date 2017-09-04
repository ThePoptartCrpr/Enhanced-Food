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
	
	private static final Map<ItemStack, ItemStack> ovenList = Maps.<ItemStack, ItemStack>newHashMap();
	public static ArrayList<OvenRecipes> recipeList = new ArrayList<OvenRecipes>();
	
	public static void registerOvenRecipes() {
		addOvenRecipe(new ItemStack(EFItems.breadDough), new ItemStack(Items.BREAD));
	}
	
	public OvenRecipes(ItemStack output, Object input) {
		this.output = output;
		this.input = input;
	}
	
	@Nullable
	public static ItemStack getOvenResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : ovenList.entrySet()) {
			if (compareItemStacks(stack, (ItemStack)entry.getKey())) {
				return (ItemStack)entry.getValue();
			}
		}
		return null;
	}
	
	private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public static void addOvenRecipe(ItemStack output, Object input) {
		ItemStack inputStack = (ItemStack) input;
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			ovenList.put(inputStack, output);
			OvenRecipes recipe = new OvenRecipes(inputStack, output);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Block input, Item output) {
		Item inputBlock = Item.getItemFromBlock(input);
		ItemStack inputStack = new ItemStack(inputBlock);
		ItemStack outputStack = new ItemStack(output);
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			ovenList.put(inputStack, outputStack);
			outputStack = new ItemStack(output);
			OvenRecipes recipe = new OvenRecipes(outputStack, input);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Item input, ItemStack output) {
		ItemStack inputStack = new ItemStack(input);
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			ovenList.put(inputStack, output);
			OvenRecipes recipe = new OvenRecipes(output, inputStack);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
	public static void addOvenRecipe(Block input, ItemStack output) {
		Item inputBlock = Item.getItemFromBlock(input);
		ItemStack inputStack = new ItemStack(inputBlock);
		if (getOvenResult(inputStack) != null) {
			Utils.getConsole().info("Conflicting recipes, ignoring recipe for " + input);
		} else {
			ovenList.put(inputStack, output);
			OvenRecipes recipe = new OvenRecipes(output, input);
			recipeList.add(recipe);
			Utils.getConsole().info(recipeList);
		}
	}
	
}
