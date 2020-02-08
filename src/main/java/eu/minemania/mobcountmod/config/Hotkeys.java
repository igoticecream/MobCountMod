package eu.minemania.mobcountmod.config;

import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;
import com.google.common.collect.ImmutableList;

/**
 * Default hotkeys configuration.
 */
public class Hotkeys
{
	public static final ConfigHotkey COUNTER = new ConfigHotkey("togglePassive", "P",  "Toggle Passive Counter");
	public static final ConfigHotkey HOSTILE = new ConfigHotkey("toggleHostile", "O",  "Toggle Hostile Counter");
	public static final ConfigHotkey OPEN_GUI_SETTINGS = new ConfigHotkey("openGuiSettings", "P,C",  "Open the Config GUI");

	public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
			COUNTER,
			HOSTILE,
			OPEN_GUI_SETTINGS
			);
}

