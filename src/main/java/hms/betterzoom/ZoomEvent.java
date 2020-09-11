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
			if (ZoomKeyBind.zoom.isKeyDown()) {
				if (this.scroll < 0.0f && this.zo < 150.0f) {
					this.zo += 2.0f;
					Minecraft.getMinecraft().gameSettings.fovSetting = this.zo;
					--Minecraft.getMinecraft().thePlayer.inventory.currentItem;
					this.scroll = 0.0f;
				} else if (this.scroll > 0.0f && this.zo > 2.0f) {
					this.zo -= 2.0f;
					Minecraft.getMinecraft().gameSettings.fovSetting = this.zo;
					++Minecraft.getMinecraft().thePlayer.inventory.currentItem;
					this.scroll = 0.0f;
				} else if (this.scroll > 0.0f) {
					++Minecraft.getMinecraft().thePlayer.inventory.currentItem;
					this.scroll = 0.0f;
				} else if (this.scroll < 0.0f) {
					--Minecraft.getMinecraft().thePlayer.inventory.currentItem;
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
			if (ZoomKeyBind.zoom.isKeyDown()) {
				if (this.publ) {
					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
							Minecraft.getMinecraft().entityRenderer, 0.03, "field_78503_V"); // field_78503_V
					this.oldFOV = Minecraft.getMinecraft().gameSettings.fovSetting;
					this.publ = false;
					if (Reference.isSmoothCameraEnabled) {
						Minecraft.getMinecraft().gameSettings.smoothCamera = true;
					}
				}
				ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
						Minecraft.getMinecraft().entityRenderer, 0.03, "field_78503_V");
				Minecraft.getMinecraft().gameSettings.fovSetting = this.zo;
				this.zoom = true;
			} else if (!ZoomKeyBind.zoom.isKeyDown() && this.zoom) {
				Minecraft.getMinecraft().gameSettings.fovSetting = this.oldFOV;
				ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class,
						Minecraft.getMinecraft().entityRenderer, 1, "field_78503_V");
				Minecraft.getMinecraft().entityRenderer.updateRenderer();
				if (Reference.isSmoothCameraEnabled) {
					Minecraft.getMinecraft().gameSettings.smoothCamera = false;
				}

				this.zo = Reference.defaultZoomLevel;
				this.scroll = 0.0f;
				this.zoom = false;
				this.publ = true;
			}
		}
	}

}
