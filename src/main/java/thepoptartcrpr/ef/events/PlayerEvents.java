package thepoptartcrpr.ef.events;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import thepoptartcrpr.ef.EnhancedFood;
import thepoptartcrpr.ef.gui.GuiHandler;
import thepoptartcrpr.ef.handlers.AchievementHandler;
import thepoptartcrpr.ef.init.EFItems;
import thepoptartcrpr.ef.utils.Utils;

public class PlayerEvents {
	
	@SubscribeEvent
	public void onItemPickup (EntityItemPickupEvent event) {
		// final ItemStack itemStack = event.pickedUp.getEntityItem();
		// final ItemStack harvestStack = new ItemStack(EFItems.corn);
		Item item = event.getItem().getEntityItem().getItem();
		if (item == EFItems.corn) {
			event.getEntityPlayer().addStat(AchievementHandler.achievementHarvest);
		} 
		else if (item == EFItems.cornSeeds) {
			event.getEntityPlayer().addStat(AchievementHandler.achievementCrop);
		}
		// Utils.getConsole().info(item + ", " + harvest);
		// if(ItemStack.areItemStacksEqual(itemStack, harvestStack) && !event.entityPlayer.hasAchievement(AchievementHandler.achievementHarvest)) {
			// event.entityPlayer.addStat(AchievementHandler.achievementHarvest);
		// }
	}
	
	@SubscribeEvent
	public void onPlayerJoinEvent(PlayerLoggedInEvent event) {
		// Utils.getConsole().info("Connected");
	}
	
	@SubscribeEvent
	public void onPlayerUseItemEvent(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		// Utils.getConsole().info(event.crafting.getItem());
		if (item == EFItems.filletedTuna) {
			// Utils.getConsole().info("filleted tuna");
		}
	}

}
