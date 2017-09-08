package thepoptartcrpr.ef.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.container.ContainerOven;
import thepoptartcrpr.ef.gui.ProgressBar.ProgressBarDirection;
import thepoptartcrpr.ef.init.EFCapabilities;
import thepoptartcrpr.ef.network.PacketGetBurnTime;
import thepoptartcrpr.ef.network.PacketGetCookTime;
import thepoptartcrpr.ef.network.PacketGetWorker;
import thepoptartcrpr.ef.network.PacketHandler;
import thepoptartcrpr.ef.tileentity.TileEntityOven;
import thepoptartcrpr.ef.utils.Utils;

public class GuiOven extends GuiContainer {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Variables.MODID, "textures/gui/container/oven.png");
	
	private TileEntityOven te;
	private IInventory playerInv;
	
	public static int burnTime = 0;
	public static int totalBurnTime = 2000;
	public static int cookTime, totalCookTime = 0;
	
	public static int sync = 0;
	
	private ProgressBar fuelBar;
	private ProgressBar progressBar;
	
	public GuiOven(IInventory playerInv, TileEntityOven te) {
		super(new ContainerOven(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
		
		// this.fuelBar = new ProgressBar(TEXTURE, ProgressBarDirection.DOWN_TO_UP, 14, 14, 8, 26, 176, 0);
		this.fuelBar = new ProgressBar(TEXTURE, ProgressBarDirection.DOWN_TO_UP, 14, 31, 9, 13, 176, 31);
		this.progressBar = new ProgressBar(TEXTURE, ProgressBarDirection.LEFT_TO_RIGHT, 24, 15, 77, 35, 176, 15);
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String string = I18n.format("container.oven");
		this.mc.fontRendererObj.drawString(string, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
		this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 73, 4210752);
	
		this.fuelBar.setMin(burnTime).setMax(2000);
		this.fuelBar.draw(this.mc);
		
		this.progressBar.setMin(cookTime).setMax(totalCookTime);
		this.progressBar.draw(this.mc);
		
		int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
		int actualMouseY = mouseY - ((this.height - this.ySize) / 2);
		
		if(actualMouseX >= 7 && actualMouseX <= 24 && actualMouseY >= 51 && actualMouseY <= 68 && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(1) == null) {
			List<String> text = new ArrayList<String>();
			text.add(TextFormatting.GRAY + I18n.format("gui.oven.fuel_bucket.tooltip"));
			this.drawHoveringText(text, actualMouseX, actualMouseY);
		}
		
		if(actualMouseX >= 9 && actualMouseX <= 22 && actualMouseY >= 13 && actualMouseY <= 43) {
			List<String> text = new ArrayList<String>();
			text.add(TextFormatting.GOLD + I18n.format("gui.oven.fuel_bar_1.tooltip") + burnTime + I18n.format("gui.oven.fuel_bar_2.tooltip") + totalBurnTime + I18n.format("gui.oven.fuel_bar_3.tooltip") + Math.floor(burnTime / 50) + I18n.format("gui.oven.fuel_bar_4.tooltip"));
			this.drawHoveringText(text, actualMouseX, actualMouseY);
		}
		
		sync++;
		sync %= 10;
		if (sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetBurnTime(this.te.getPos(), "thepoptartcrpr.ef.gui.GuiOven", "burnTime"));
			PacketHandler.INSTANCE.sendToServer(new PacketGetCookTime(this.te.getPos(), "thepoptartcrpr.ef.gui.GuiOven", "cookTime", "totalCookTime"));
		}
	}

}
