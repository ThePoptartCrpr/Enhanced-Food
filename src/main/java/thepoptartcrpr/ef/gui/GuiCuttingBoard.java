package thepoptartcrpr.ef.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import thepoptartcrpr.ef.Variables;
import thepoptartcrpr.ef.container.ContainerCuttingBoard;
import thepoptartcrpr.ef.gui.ProgressBar.ProgressBarDirection;
import thepoptartcrpr.ef.network.PacketGetBurnTime;
import thepoptartcrpr.ef.network.PacketGetCookTime;
import thepoptartcrpr.ef.network.PacketGetCuttingBoard;
import thepoptartcrpr.ef.network.PacketHandler;
import thepoptartcrpr.ef.tileentity.TileEntityCuttingBoard;

public class GuiCuttingBoard extends GuiContainer {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(Variables.MODID, "textures/gui/container/cutting_board.png");
	
	private TileEntityCuttingBoard te;
	private IInventory playerInv;
	
	public static int cutTime, totalCutTime = 0;
	
	public static int sync = 0;
	
	private ProgressBar progressBar;
	
	public GuiCuttingBoard(IInventory playerInv, TileEntityCuttingBoard te) {
		super(new ContainerCuttingBoard(playerInv, te));
		
		this.xSize = 176;
		this.ySize = 166;
		
		this.te = te;
		this.playerInv = playerInv;
		
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
		String string = I18n.format("container.cutting_board");
		this.mc.fontRendererObj.drawString(string, this.xSize / 2 - this.mc.fontRendererObj.getStringWidth(string) / 2, 6, 4210752);
		this.mc.fontRendererObj.drawString(this.playerInv.getDisplayName().getFormattedText(), 8, 73, 4210752);
		
		this.progressBar.setMin(cutTime).setMax(totalCutTime);
		this.progressBar.draw(this.mc);
		
		sync++;
		sync %= 10;
		if (sync == 0) {
			PacketHandler.INSTANCE.sendToServer(new PacketGetCuttingBoard(this.te.getPos(), "thepoptartcrpr.ef.gui.GuiCuttingBoard", "cutTime", "totalCutTime"));
		}
	}
	
}