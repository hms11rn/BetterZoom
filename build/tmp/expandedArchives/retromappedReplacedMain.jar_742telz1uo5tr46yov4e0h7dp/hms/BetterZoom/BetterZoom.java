package hms.betterzoom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hms.betterzoom.Commands.Settings;
import hms.betterzoom.ref.Reference;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, guiFactory = "hms.betterzoom.gui.Config")
public class BetterZoom {

//	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
//	public static CommonProxy proxy;

	@Mod.Instance("btz")
	public static BetterZoom instance;

	public static ZoomEvent zeInstance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (doesConfigExist() && isFileValid()) {
			System.out.println("BetterZoom Config exists");
		} else {
			createConfigFile();
		}
		ZoomEvent ze = new ZoomEvent();
		BetterZoom.zeInstance = ze;
		ZoomKeyBind.register();
		ClientCommandHandler.instance.func_71560_a(new Settings());
		MinecraftForge.EVENT_BUS.register(ze);

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println("BetterZoom Version " + Reference.VERSION + " Loaded");

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Reference.update();
	}
	
	/**
	 * A restart occurs when an exception was thrown.
	 */
	public void restart() {
		if (doesConfigExist()) {
			File f = new File(Loader.instance().getConfigDir() + "/BetterZoomConfig.cfg");
			if (f.delete()) {
				createConfigFile();
			}
		} else {

		}
	}

	private boolean doesConfigExist() {
		File configFile = new File(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg");
		return configFile.exists();
	}

	@SuppressWarnings("unused")
	private boolean isFileValid() {
		try {
			boolean testToggled = Boolean.parseBoolean(Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(0).replace("[IsToggled]", ""));
			boolean testSmooth = Boolean.parseBoolean(Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(1).replace("[IsSmoothZoomEnabled]", ""));
			int testDefaultZL = Integer.parseInt(Files
					.readAllLines(
							Paths.get(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg"))
					.get(2).replace("[DefaultZoomLevel]", ""));
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	private boolean createConfigFile() { // If it returns false it
											// means an error occurred.
		try {
			File config = new File(Loader.instance().getConfigDir().getAbsolutePath() + "/BetterZoomConfig.cfg");
			if (config.createNewFile()) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(config));
				writer.append(("[IsToggled]" + String.valueOf(true)));
				writer.newLine();
				writer.append(("[IsSmoothCameraEnabled]" + String.valueOf(true)));
				writer.newLine();
				writer.append(("[DefaultZoomLevel]" + String.valueOf(20)));
				writer.newLine();
				writer.close();
				Reference.update();
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false; // Not supposed to ever reach this point, if it does it returns false.
	}
}
