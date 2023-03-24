package hms.betterzoom.ref;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import hms.betterzoom.BetterZoom;
import net.minecraftforge.fml.common.Loader;

public class Reference {
	public static final String MODID = "btz";
	public static final String VERSION = "2.0";
	public static final String NAME = "BetterZoom";
	public static int defaultZoomLevel;
	public static boolean isSmoothCameraEnabled;
	public static boolean isModToggled;

	//TODO change to forge config.
	public static boolean isModToggled() {
		try {
			final String isModToggled = Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(0).replace("[IsToggled]", "");
			final boolean returnValue = Boolean.parseBoolean(isModToggled);
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
			return false;
		}
	}

	public static boolean isSmoothCameraEnabled() {
		try {
			final String smoothCameraValue = Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(1).replace("[IsSmoothCameraEnabled]", "");
			final boolean returnValue = Boolean.parseBoolean(smoothCameraValue);
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
			return true;
		}
	}

	public static int getDefaultZoomLevel() {
		try {
			final String defaultZoomLevel = Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(2).replace("[DefaultZoomLevel]", "");
			final int returnValue = Integer.parseInt(defaultZoomLevel);
			return returnValue;
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
			return 40;
		}

	}

	//TODO change to forge config
	public static void setSmoothZoom(final boolean smoothCameraValue) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(
					new FileWriter(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(isModToggled)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(smoothCameraValue)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
			update();
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
		}
	}

	//TODO change to forge config
	public static void setToggled(final boolean modToggledValue) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(
					new FileWriter(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(modToggledValue)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(isSmoothCameraEnabled)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
			update();
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
		}
	}

	//TODO change to forge config
	public static void setDefaultZoomLevel(final int defaultZoomLevel) {
		try {
			update();
			final BufferedWriter writer = new BufferedWriter(
					new FileWriter(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"));
			writer.append(("[IsToggled]" + String.valueOf(isModToggled)));
			writer.newLine();
			writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(isSmoothCameraEnabled)));
			writer.newLine();
			writer.append(("[DefaultZoomLevel]" + String.valueOf(defaultZoomLevel)));
			writer.newLine();
			writer.close();
			update();
		} catch (Exception e) {
			e.printStackTrace();
			BetterZoom.instance.restart();
		}
	}

	public static void update() {
		Reference.isModToggled = isModToggled();
		Reference.isSmoothCameraEnabled = isSmoothCameraEnabled();
		Reference.defaultZoomLevel = getDefaultZoomLevel();
		BetterZoom.instance.zeInstance.zo = defaultZoomLevel;
	}
}
