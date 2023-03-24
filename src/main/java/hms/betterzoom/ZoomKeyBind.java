package hms.betterzoom;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ZoomKeyBind {

	public static KeyBinding zoom;

	/**
	 * Registers keybind to Minecraft
	 */
	public static void register() {
		zoom = new KeyBinding("Better Zoom", Keyboard.KEY_C, "key.categories.misc");
		ClientRegistry.registerKeyBinding(zoom);
	}
}