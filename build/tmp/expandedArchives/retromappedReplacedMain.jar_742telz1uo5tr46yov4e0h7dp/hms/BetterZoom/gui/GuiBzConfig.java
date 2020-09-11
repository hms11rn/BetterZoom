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
	public void func_73866_w_() {
		super.func_73866_w_();
		toggle = new GuiButton(0, field_146294_l / 2 + 15, 62, 20, 13,
				Settings.convertBoolToString(Reference.isModToggled, "off", "on")); // - 55
		toggleSmooth = new GuiButton(0, field_146294_l / 2 + 15, 87, 20, 13,
				Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on")); // - 55
		close = new GuiButton(1, field_146294_l / 2 - 20, field_146295_m - 100, 60, 20, "Close"); // - 95
		defaultZl = new GuiSlider(2, field_146294_l / 2 - 55, 109, 130, 20, "Default Zoom Level: ", "", 3, 100,
				Reference.defaultZoomLevel, false, true);

		field_146292_n.add(toggle);
		field_146292_n.add(toggleSmooth);
		field_146292_n.add(defaultZl);
		field_146292_n.add(close);
	}

	@Override
	protected void func_146284_a(GuiButton button) throws IOException {
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
	protected void func_73869_a(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Reference.setDefaultZoomLevel(this.defaultZl.getValueInt());
			MinecraftForge.EVENT_BUS.register(this);
		}
		super.func_73869_a(typedChar, keyCode);
	}

	@Override
	public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_146278_c(0);
		this.func_73731_b(field_146289_q, "BetterZoom ", field_146294_l / 2 - 55, 65, -2039584); // - 120
		this.func_73731_b(field_146289_q, "Smoot Camera", field_146294_l / 2 - 55, 90, -2039584); // - 120
		toggle.field_146126_j = Settings.convertBoolToString(Reference.isModToggled, "off", "on");
		toggleSmooth.field_146126_j = Settings.convertBoolToString(Reference.isSmoothCameraEnabled, "off", "on");
		super.func_73863_a(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.func_73864_a(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean func_73868_f() {
		return true;
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		this.field_146297_k.func_147108_a(this.parent);
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}