package de.scribble.lp.TASTools.savestates.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiSavestateLoadingScreen extends GuiScreen{

	private static boolean copying;
	private static boolean deleting;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		fontRenderer.drawStringWithShadow("Loading a savestate!", width / 2 -50, height / 4 + 50 + -16, 0xFFFFFF);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}