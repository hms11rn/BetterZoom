package hms.betterzoom.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import hms.betterzoom.Commands.Settings;
import hms.betterzoom.ref.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class GuiBzConfig extends GuiScreen {

	GuiButton close;
	GuiButton toggle;
	GuiButton toggleSmooth;
	GuiSlider defaultZl;
	GuiScreen parent;

	public GuiBzConfig(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {
		super.initGui();
		toggle = new GuiButton(0, width / 2 + 15, 62, 20, 13,
				Settings.convertBoolToString(Reference.isModToggled, "off", "on")); // - 55
		toggleSmooth = new GuiButton(0, width / 2 + 15, 87, 20, 13,
				Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on")); // - 55
		close = new GuiButton(1, width / 2 - 20, height - 100, 60, 20, "Close"); // - 95
		defaultZl = new GuiSlider(2, width / 2 - 55, 109, 130, 20, "Default Zoom Level: ", "", 3, 100,
				Reference.defaultZoomLevel, false, true);

		buttonList.add(toggle);
		buttonList.add(toggleSmooth);
		buttonList.add(defaultZl);
		buttonList.add(close);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == close) {
			Reference.setDefaultZoomLevel(this.defaultZl.getValueInt());
			MinecraftForge.EVENT_BUS.register(this);
		} else if (button == toggle) {
			Reference.setToggled(!Reference.isModToggled);
		} else if (button == toggleSmooth) {
			Reference.setSmoothZoom(!Reference.isSmoothCameraEnabled);
		} else if (button == defaultZl) {
			Reference.setDefaultZoomLevel(defaultZl.getValueInt());
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Reference.setDefaultZoomLevel(this.defaultZl.getValueInt());
			MinecraftForge.EVENT_BUS.register(this);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground(0);
		this.drawString(fontRendererObj, "BetterZoom ", width / 2 - 55, 65, -2039584); // - 120
		this.drawString(fontRendererObj, "Smoot Camera", width / 2 - 55, 90, -2039584); // - 120
		toggle.displayString = Settings.convertBoolToString(Reference.isModToggled, "off", "on");
		toggleSmooth.displayString = Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on");
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		this.mc.displayGuiScreen(this.parent);
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}