package eu.minemania.mobcountmod.config;

import fi.dy.masa.malilib.config.IConfigHandler;

import java.io.File;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import eu.minemania.mobcountmod.Reference;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

public class Configs implements IConfigHandler {
	/**
	 * Config file for mod.
	 */
	private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

	/**
	 * Default Generic configuration.
	 */
	public static class Generic {
		public static final ConfigBoolean ENABLED = new ConfigBoolean("enabled", true, "Enables MobCountMod fully");
		public static final ConfigStringList MESSAGE_LIST = new ConfigStringList("messageList", ImmutableList.of(), "List to notify players");
		public static final ConfigString SOUNDFILE = new ConfigString("soundFile", "block.note_block.bass", "Sound file");
		public static final ConfigBoolean NOTIFYFACTION = new ConfigBoolean("notifyFaction", false, "When enabled, notifies in faction chat");
		public static final ConfigBoolean XP5 = new ConfigBoolean("xp5", false, "When disabled, uses normal mob counter radius");

		public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
				ENABLED,
				MESSAGE_LIST,
				SOUNDFILE,
				NOTIFYFACTION,
				XP5
				);
	}

	/**
	 * Loads configurations from configuration file.
	 */
	public static void loadFromFile() {
		File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

		if(configFile.exists() && configFile.isFile() && configFile.canRead()) {
			JsonElement element = JsonUtils.parseJsonFile(configFile);

			if(element != null && element.isJsonObject()) {
				JsonObject root = element.getAsJsonObject();

				ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
				ConfigUtils.readConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
			}
		}
	}
	
	/**
	 * Saves configurations to configuration file.
	 */
	public static void saveToFile() {
		File dir = FileUtils.getConfigDirectory();

		if((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
			JsonObject root = new JsonObject();

			ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
			ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

			JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
		}
	}
	@Override
	public void load() {
		loadFromFile();
	}

	@Override
	public void save() {
		saveToFile();
	}

}
