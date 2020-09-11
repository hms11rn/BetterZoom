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

public class Settings extends CommandBase {
	public static String bz;
	public boolean silentCommand = false;

	public String func_71517_b() {
		return "betterzoom";
	}

	public String func_71518_a(final ICommandSender sender) {
		return "/";
	}

	public void func_71515_b(final ICommandSender sender, final String[] args) throws CommandException {
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
			} else if (args[0].equalsIgnoreCase("reload")) {
				addMessage(getMessage(bz + "BetterZoom reloaded"));
			} else {
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
	public boolean func_71519_b(final ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> func_180525_a(final ICommandSender sender, final String[] args, final BlockPos pos) {
		if (args.length == 1) {
			final List<String> values = new ArrayList<String>();
			values.add("DefaultZoomLevel");
			values.add("SmoothCamera");
			values.add("toggle");
			values.add("on");
			values.add("off");
			return func_175762_a(args, values);
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("SmoothCamera")) {
				return func_71530_a(args, new String[] { "enable", "disable" });
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

	private void addMessage(ChatComponentText message) {
		if (silentCommand == true) {
			silentCommand = false;
			return;
		}
		Minecraft.func_71410_x().field_71439_g.func_145747_a(message);
	}

	public boolean validateZoomLevel(int i) {
		if (i > 2) {
			return true;
		} else {
			return false;
		}
	}

	public static String convertBoolToString(boolean bool, String falseV, String trueV) {
		if (bool == true) {
			return trueV;
		} else {
			return falseV;
		}
	}

	public ChatComponentText getMessage(String message) {
		ChatComponentText ret = new ChatComponentText(message);
		ret.func_150256_b().func_150238_a(EnumChatFormatting.AQUA);
		return ret;
	}

	@Override
	public String toString() {

		return "[" + this.getClass().toString() + ",name=" + this.func_71517_b() + ",canCommandSenderUseCommand"
				+ "true" + ",getCommandUsage=\"/\"" + "]";
	}

	@SubscribeEvent
	public void onTick(ClientTickEvent e) {
		Minecraft.func_71410_x().func_147108_a(new MainGui());
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
