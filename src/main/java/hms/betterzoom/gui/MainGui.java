package hms.betterzoom.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import hms.betterzoom.Commands.Settings;
import hms.betterzoom.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {
	float oldFov = 0;
	GuiModernButton close;
	GuiModernButton toggle;
	GuiModernButton toggleSmooth;
	GuiModernSlider defaultZl;

	public void closeScreen() {
		mc.thePlayer.closeScreen();
		Reference.setDefaultZoomLevel(defaultZl.getValueInt());
		mc.gameSettings.fovSetting = oldFov;
	}

	public MainGui() {
		super();
		oldFov = Minecraft.getMinecraft().gameSettings.fovSetting;
	}

	@Override
	public void initGui() {
		super.initGui();
		toggle = new GuiModernButton(0, width / 2 + 15, 62, 20, 13,
				Settings.convertBoolToString(Reference.isModToggled, "off", "on")); // - 55
		toggleSmooth = new GuiModernButton(0, width / 2 + 15, 87, 20, 13,
				Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on")); // - 55
		close = new GuiModernButton(1, width / 2 - 20, height - 100, 60, 20, "Close"); // - 95
		defaultZl = new GuiModernSlider(2, width / 2 - 55, 109, 130, 20, "Default Zoom Level: ", "", 3, 100,
				Reference.defaultZoomLevel, false, true);

		buttonList.add(toggle);
		buttonList.add(toggleSmooth);
		buttonList.add(defaultZl);
		buttonList.add(close);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button == close) {
			this.closeScreen();
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
			this.closeScreen();
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("btz:textures/gui/Background.png"));
		this.drawTexturedModalRect(width / 2 - 65, 55, 310, 88, 150, height - 125); // - 130
		this.drawString(fontRendererObj, "BetterZoom ", width / 2 - 55, 65, -2039584); // - 120
		this.drawString(fontRendererObj, "Smoot Camera", width / 2 - 55, 90, -2039584); // - 120
		toggle.displayString = Settings.convertBoolToString(Reference.isModToggled, "off", "on");
		toggleSmooth.displayString = Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on");
		mc.gameSettings.fovSetting = defaultZl.getValueInt();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseX < width / 2 - 70) {
			this.closeScreen();
		} else if (mouseX > width / 2 + 80) {
			this.closeScreen();
		} else {
			if (mouseY < 62) {
				this.closeScreen();
			} else if (mouseY > 140) {
				this.closeScreen();
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}