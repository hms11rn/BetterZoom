package hms.betterzoom.Commands;

import java.util.ArrayList;
import java.util.List;

import hms.betterzoom.gui.MainGui;
import hms.betterzoom.ref.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

/**
 * Command class
 */
public class Settings extends CommandBase {
	/**
	 * Betterzoom text.
	 */
	public static String bz;
	public boolean silentCommand = false;

	public String getCommandName() {
		return "betterzoom";
	}

	public String getCommandUsage(final ICommandSender sender) {
		return "/";
	}

	/**
	 * There is a gui, but also a command <br>
	 * for the first argument there can be 3 options
	 * off - turn off betterzoom, on - turn on betterzoom, smoothcamera - turn on and off smooth camera.
	 * the third argument is for typing disabled/enabled<br>
	 * there is a third argument "-s" that was for during testing, but decided to keep it
	 * @param sender
	 * @param args
	 * @throws CommandException
	 */
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length == 3) {
			if (args[2].equalsIgnoreCase("-s")) {
				silentCommand = true;
			}
		}
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("toggle")) {
				Reference.setToggled(!Reference.isModToggled);
				addMessage(getMessage(
						bz + "BetterZoom is now " + convertBoolToString(Reference.isModToggled, "off", "on")));
			} else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
				Reference.setToggled(false);
				addMessage(getMessage(bz + "BetterZoom is now off."));
			} else if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) {
				Reference.setToggled(true);
				addMessage(getMessage(bz + "BetterZoom is now on."));
			} else if (args[0].equalsIgnoreCase("SmoothCamera")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("on")) {
						Reference.setSmoothZoom(true);
						addMessage(getMessage("Smooth Camera is now going to be on when you zoom."));
					} else if (args[1].equalsIgnoreCase("disable") || args[1].equalsIgnoreCase("off")) {
						Reference.setSmoothZoom(false);
						addMessage(getMessage("Smooth Camera is now going to be off when you zoom."));
					} else {
						addMessage(getMessage(bz + "The value of SmoothCamera must be either enable or disable"));
					}
				} else {
					addMessage(getMessage(bz + "The value of SmoothCamera must be either enable or disable"));
				}
			} else if (args[0].equalsIgnoreCase("DefaultZoomLevel")) {
				if (args.length > 1) {
					try {
						if (validateZoomLevel(Integer.parseInt(args[1]))) {
							int defaultZoom = Integer.parseInt(args[1]);
							Reference.setDefaultZoomLevel(defaultZoom);
						} else {
							addMessage(getMessage(bz + "The default zoom level must be greater then 1."));
						}
					} catch (NumberFormatException e) {
						addMessage(getMessage(bz + args[1] + "is not a valid number."));
					}
				} else {
					addMessage(getMessage(bz + "Usage: /betterzoom DefaultZoomLevel <Number>"));
				}
			} else if (args[0].equalsIgnoreCase("scrollwheel")) {
				Reference.setIsScrollWheelToggled(!Reference.checkScrollWheelToggled);
				addMessage(getMessage(bz + "Scroll to Zoom is now " + Reference.checkScrollWheelToggled + "!"));
			}
			else if (args[0].equalsIgnoreCase("reload")) {
				addMessage(getMessage(bz + "BetterZoom reloaded"));
			}
			else {
				addMessage(getMessage(bz + "======"));
				addMessage(getMessage("Available commands"));
				addMessage(getMessage("/betterzoom toggle - toggle the mod"));
				addMessage(getMessage("/betterzoom on - turn the mod on"));
				addMessage(getMessage("/betterzoom off - turn the mod off"));
				addMessage(getMessage("/betterzoom SmoothCamera <enable, disable>"));
				addMessage(getMessage("/betterzoom DefaultZoomLevel <number>"));
				addMessage(getMessage("/betterzoom - Open Gui"));
				addMessage(getMessage("============"));
			}
			Reference.update();
		} else {
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return true;
	}

	/**
	 * Tab completion, so you can press 'tab' and get completions for it.
	 * @param sender
	 * @param args
	 * @param pos
	 * @return
	 */
	@Override
	public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		if (args.length == 1) {
			final List<String> values = new ArrayList<String>();
			values.add("DefaultZoomLevel");
			values.add("SmoothCamera");
			values.add("toggle");
			values.add("on");
			values.add("off");
			return getListOfStringsMatchingLastWord(args, values);
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("SmoothCamera")) {
				return getListOfStringsMatchingLastWord(args, new String[] { "enable", "disable" });
			}
			return new ArrayList<String>();
		} else {
			return new ArrayList<String>();
		}
	}

	static {
		Settings.bz = (EnumChatFormatting.BLACK + "[" + EnumChatFormatting.GREEN + "BetterZoom" // BetterZoom[] 2
				+ EnumChatFormatting.BLACK + "] " + EnumChatFormatting.AQUA + "");
	}

	/**
	 * Adds message unless -s tag is called with command.
	 * @param message
	 */
	private void addMessage(ChatComponentText message) {
		if (silentCommand == true) {
			silentCommand = false;
			return;
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(message);
	}

	/**
	 * Validates zoom level, zoom level can't be smaller than 2
	 * @param i
	 * @return
	 */
	public boolean validateZoomLevel(int i) {
		if (i > 2)
			return true;
		 else
			return false;
	}

	/**
	 * Little util command to convert a boolean to one of 2 strings
	 * @param bool boolean
	 * @param falseV text to return if boolean is false
	 * @param trueV text to return if boolean is true
	 * @return
	 */
	public static String convertBoolToString(boolean bool, String falseV, String trueV) {
		if (bool == true)
			return trueV;
		else
			return falseV;

	}

	public ChatComponentText getMessage(String message) {
		ChatComponentText ret = new ChatComponentText(message);
		ret.getChatStyle().setColor(EnumChatFormatting.AQUA);
		return ret;
	}

	/**
	 * Custom to string.
	 * @return
	 */
	@Override
	public String toString() {

		return "[" + this.getClass().toString() + ",name=" + this.getCommandName() + ",canCommandSenderUseCommand"
				+ "true" + ",getCommandUsage=\"/\"" + "]";
	}

	/**
	 * Tick event. <br> There is a bug in Minecraft that if you try to open a GUI,
	 * when code is not synced with a tick, it won't open, therefore the solution is
	 * to set a Tick event and when you are trying to open a GUI, you Register the class
	 * to forge Event Bus, then tick event is run, and you can open GUI while being synced with a Tick.
	 *<br><br>
	 * I saw this trick in Sk1er's discord server.
	 * @param e Event input, not used here.
	 */
	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		Minecraft.getMinecraft().displayGuiScreen(new MainGui());
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
