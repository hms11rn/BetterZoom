package hms.betterzoom;

import hms.betterzoom.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

/**
 * @author hms11
 */
public class ZoomEvent {
	boolean zoom;
	public float zo = Reference.defaultZoomLevel;
	float scroll = 0;
	boolean smoothC;
	boolean publ;
	float oldFOV;

	public ZoomEvent() {
		this.zoom = false;
		this.zo = 25.0f;
		this.publ = true;
	}

	/**
	 * Makes sure that the hotbar items doesn't scroll.
	 */
	@SubscribeEvent
	public void onMouseEvent(final MouseEvent event) {
		if (Reference.isModToggled) {
			this.scroll = (float) event.dwheel;
			if (ZoomKeyBind.zoom.func_151470_d()) {
				if (this.scroll < 0.0f && this.zo < 150.0f) {
					this.zo += 2.0f;
					Minecraft.func_71410_x().field_71474_y.field_74334_X = this.zo;
					--Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70461_c;
					this.scroll = 0.0f;
				} else if (this.scroll > 0.0f && this.zo > 2.0f) {
					this.zo -= 2.0f;
					Minecraft.func_71410_x().field_71474_y.field_74334_X = this.zo;
					++Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70461_c;
					this.scroll = 0.0f;
				} else if (this.scroll > 0.0f) {
					++Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70461_c;
					this.scroll = 0.0f;
				} else if (this.scroll < 0.0f) {
					--Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70461_c;
					this.scroll = 0.0f;
				}
			}
		}
	}

	/**
	 * Every game tick it checks that if {@code ZoomKeyBind.zoom.iskeyDown}
	 */
	@SubscribeEvent
	public void tickUpdate(final ClientTickEvent e) {
		if (Reference.isModToggled) {
			if (ZoomKeyBind.zoom.func_151470_d()) {
				if (this.publ) {
					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
							Minecraft.func_71410_x().field_71460_t, 0.03, "field_78503_V"); // field_78503_V
					this.oldFOV = Minecraft.func_71410_x().field_71474_y.field_74334_X;
					this.publ = false;
					if (Reference.isSmoothCameraEnabled) {
						Minecraft.func_71410_x().field_71474_y.field_74326_T = true;
					}
				}
				ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
						Minecraft.func_71410_x().field_71460_t, 0.03, "field_78503_V");
				Minecraft.func_71410_x().field_71474_y.field_74334_X = this.zo;
				this.zoom = true;
			} else if (!ZoomKeyBind.zoom.func_151470_d() && this.zoom) {
				Minecraft.func_71410_x().field_71474_y.field_74334_X = this.oldFOV;
				ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
						Minecraft.func_71410_x().field_71460_t, 1, "field_78503_V");
				Minecraft.func_71410_x().field_71460_t.func_78464_a();
				if (Reference.isSmoothCameraEnabled) {
					Minecraft.func_71410_x().field_71474_y.field_74326_T = false;
				}

				this.zo = Reference.defaultZoomLevel;
				this.scroll = 0.0f;
				this.zoom = false;
				this.publ = true;
			}
		}
	}

}
