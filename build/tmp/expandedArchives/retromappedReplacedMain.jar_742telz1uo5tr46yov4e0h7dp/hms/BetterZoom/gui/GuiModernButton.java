package hms.betterzoom.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiModernButton extends GuiButton {
	protected static final ResourceLocation buttonTextures = new ResourceLocation("btz:textures/gui/widgets.png");

	public GuiModernButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, 200, 20, buttonText);
	}

	public GuiModernButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	/**
	 * Draws this button to the screen.
	 */
	public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
		if (this.field_146125_m) {
			FontRenderer fontrenderer = mc.field_71466_p;
			mc.func_110434_K().func_110577_a(buttonTextures);
			GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_146123_n = mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f
					&& mouseY < this.field_146129_i + this.field_146121_g;
			int i = this.func_146114_a(this.field_146123_n);
			GlStateManager.func_179147_l();
			GlStateManager.func_179120_a(770, 771, 1, 0);
			GlStateManager.func_179112_b(770, 771);
			this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
			this.func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2,
					46 + i * 20, this.field_146120_f / 2, this.field_146121_g);
			this.func_146119_b(mc, mouseX, mouseY);
			int j = 14737632;

			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.field_146124_l) {
				j = 10526880;
			} else if (this.field_146123_n) {
				j = 16777120;
			}

			this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2,
					this.field_146129_i + (this.field_146121_g - 8) / 2, j);
		}
	}
}