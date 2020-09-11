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
		field_146297_k.field_71439_g.func_71053_j();
		Reference.setDefaultZoomLevel(defaultZl.getValueInt());
		field_146297_k.field_71474_y.field_74334_X = oldFov;
	}

	public MainGui() {
		super();
		oldFov = Minecraft.func_71410_x().field_71474_y.field_74334_X;
	}

	@Override
	public void func_73866_w_() {
		super.func_73866_w_();
		toggle = new GuiModernButton(0, field_146294_l / 2 + 15, 62, 20, 13,
				Settings.convertBoolToString(Reference.isModToggled, "off", "on")); // - 55
		toggleSmooth = new GuiModernButton(0, field_146294_l / 2 + 15, 87, 20, 13,
				Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on")); // - 55
		close = new GuiModernButton(1, field_146294_l / 2 - 20, field_146295_m - 100, 60, 20, "Close"); // - 95
		defaultZl = new GuiModernSlider(2, field_146294_l / 2 - 55, 109, 130, 20, "Default Zoom Level: ", "", 3, 100,
				Reference.defaultZoomLevel, false, true);

		field_146292_n.add(toggle);
		field_146292_n.add(toggleSmooth);
		field_146292_n.add(defaultZl);
		field_146292_n.add(close);
	}

	@Override
	protected void func_146284_a(GuiButton button) throws IOException {
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
	protected void func_73869_a(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			this.closeScreen();
		}
		super.func_73869_a(typedChar, keyCode);
	}

	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
		Minecraft.func_71410_x().field_71446_o.func_110577_a(new ResourceLocation("btz:textures/gui/Background.png"));
		this.func_73729_b(field_146294_l / 2 - 65, 55, 310, 88, 150, field_146295_m - 125); // - 130
		this.func_73731_b(field_146289_q, "BetterZoom ", field_146294_l / 2 - 55, 65, -2039584); // - 120
		this.func_73731_b(field_146289_q, "Smoot Camera", field_146294_l / 2 - 55, 90, -2039584); // - 120
		toggle.field_146126_j = Settings.convertBoolToString(Reference.isModToggled, "off", "on");
		toggleSmooth.field_146126_j = Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on");
		field_146297_k.field_71474_y.field_74334_X = defaultZl.getValueInt();
		super.func_73863_a(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (mouseX < field_146294_l / 2 - 70) {
			this.closeScreen();
		} else if (mouseX > field_146294_l / 2 + 80) {
			this.closeScreen();
		} else {
			if (mouseY < 62) {
				this.closeScreen();
			} else if (mouseY > 140) {
				this.closeScreen();
			}
		}
		super.func_73864_a(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean func_73868_f() {
		return true;
	}
}