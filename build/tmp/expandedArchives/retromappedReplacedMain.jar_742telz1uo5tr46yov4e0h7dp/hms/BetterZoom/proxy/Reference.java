package hms.BetterZoom.proxy;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hms.BetterZoom.BetterZoom;
import hms.BetterZoom.ZoomEvent;
import net.minecraft.client.Minecraft;

public class Reference {
	public static final String MODID = "btz";
	public static final String VERSION = "1.7";
	public static final String NAME = "BetterZoom";
	public static final String SERVER_PROXY_CLASS = "hms.BetterZoom.proxy.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "hms.BetterZoom.proxy.ClientProxy";
	public static int defaultZoomLevel;
	public static boolean isSmoothCameraEnabled;
	public static boolean isModToggled;

	public static boolean isModToggled() {
		try {
			final String isModToggled = Files.readAllLines(Paths.get("config/BetterZoomConfig.cfg")).get(0).replace("[IsToggled]", "");
			final boolean returnValue = Boolean.parseBoolean(isModToggled);
			return returnValue;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isSmoothCameraEnabled() {
		try {
			final String smoothCameraValue = Files.readAllLines(Paths.get("config/BetterZoomConfig.cfg")).get(1)
					.replace("[IsSmoothCameraEnabled]", "");
			final boolean returnValue = Boolean.parseBoolean(smoothCameraValue);
			return returnValue;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
	}

	public static int getDefaultZoomLevel() {
		try {
			final String defaultZoomLevel = Files.readAllLines(Paths.get("config/BetterZoomConfig.cfg")).get(2)
					.replace("[DefaultZoomLevel]", "");
			final int returnValue = Integer.parseInt(defaultZoomLevel);
			return returnValue;
		} catch (IOException e) {
			e.printStackTrace();
			return 40;
		}

	}

	public static void setSmoothZoom(final boolean smoothCameraValue) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(new FileWriter("config/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(isModToggled)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(smoothCameraValue)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setToggled(final boolean modToggledValue) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(new FileWriter("config/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(modToggledValue)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(isSmoothCameraEnabled)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setDefaultZoomLevel(final int defaultZoomLevel) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(new FileWriter("config/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(isModToggled)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(isSmoothCameraEnabled)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void update() {
		Reference.isModToggled = isModToggled();
		Reference.isSmoothCameraEnabled = isSmoothCameraEnabled();
		Reference.defaultZoomLevel = getDefaultZoomLevel();
		BetterZoom.zeInstance.zo = defaultZoomLevel;
	}
}
