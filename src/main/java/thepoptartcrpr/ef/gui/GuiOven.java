package thepoptartcrpr.ef.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
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
	
	// public static int Cooldown, maxCooldown = 0;
	public static int burnTime = 0;
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
		this.progressBar = new ProgressBar(TEXTURE, ProgressBarDirection.LEFT_TO_RIGHT, 24, 17, 77, 34, 176, 14);
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
	
		this.fuelBar.setMin(burnTime).setMax(3000);
		this.fuelBar.draw(this.mc);
		
		this.progressBar.setMin(cookTime).setMax(totalCookTime);
		this.progressBar.draw(this.mc);
		
		sync++;
		sync %= 10;
		if (sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetBurnTime(this.te.getPos(), "thepoptartcrpr.ef.gui.GuiOven", "burnTime"));
			PacketHandler.INSTANCE.sendToServer(new PacketGetCookTime(this.te.getPos(), "thepoptartcrpr.ef.gui.GuiOven", "cookTime", "totalCookTime"));
		}
		// this.mc.fontRendererObj.drawString(cookTime + " / " + totalCookTime, -50, 0, 0xFFFFFF);
		// Utils.getConsole().info("Cooldown: " + cookTime);
		// Utils.getConsole().info("Max cooldown: " + totalCookTime);
	}

}
