package eu.minemania.mobcountmod.counter;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import eu.minemania.mobcountmod.MobCountMod;
import eu.minemania.mobcountmod.Reference;
import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.gui.GuiConfigs.ConfigGuiTab;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.WorldUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DataManager
{
    private static DataManager INSTANCE = new DataManager();
    private static boolean staff = false;
    private static boolean rebel = false;
    private static boolean canSave;

    private final MobCounter counter = new MobCounter(isStaff());

    private int counterVisible = 0; // 0 - not visible, 1 - compact, 2 - expanded
    private int hostileVisible = 0;
    private int playSoundCount = 0; // counts up so sound plays once per sec
    private int sendMsgCount = 0; // counts up so message sends every 5 minutes

    private final String[] passives = {"Chickens: ", "Pigs: ", "Sheep: ", "Cows: ", "Horses: ", "Rabbits: ", "Wolves: ", "Ocelots: ", "Parrots: ", "Bats: ", "Cats: ", "Iron Golems: ", "Snow Golems: ", "Players: ", "Fishes: ", "Traders: ", "Dolphins: ", "Foxes: ", "Pandas: ", "Polarbears: ", "Squids: ", "Turtles: ", "Bee: "};
    private final String[] hostiles = {"Zombies: ", "Blazes: ", "Endermans: ", "Spiders: ", "Creepers: ", "Witches: ", "Endermites: ", "Slimes: ", "Guardians: ", "Illagers: ", "Ghasts: ", "Phantoms: ", "Ravagers: ", "Shulkers: ", "Silverfishes: ", "Skeletons: ", "Vexes: "};

    public static DataManager getInstance()
    {
        return INSTANCE;
    }

    public static boolean isStaff()
    {
        return staff;
    }

    public static MobCounter getCounter()
    {
        return getInstance().counter;
    }

    public static int visibleCounter()
    {
        return getInstance().counterVisible;
    }

    public static void upVisibleCounter()
    {
        getInstance().counterVisible++;
    }

    public static void resetVisibleCounter()
    {
        getInstance().counterVisible = 0;
    }

    public static int visibleHostile()
    {
        return getInstance().hostileVisible;
    }

    public static void upVisibleHostile()
    {
        getInstance().hostileVisible++;
    }

    public static void resetVisibleHostile()
    {
        getInstance().hostileVisible = 0;
    }

    public static String getHostile(int hostileId)
    {
        return getInstance().hostiles[hostileId];
    }

    public static String getPassive(int passiveId)
    {
        return getInstance().passives[passiveId];
    }

    public static void setPlaySoundCount(int count)
    {
        getInstance().playSoundCount = count;
    }

    public void stuffToggle()
    {
        staff = !staff;
    }

    public void rubbleToggle()
    {
        rebel = !rebel;
    }

    private static ConfigGuiTab configGuiTab = ConfigGuiTab.GENERIC;

    public static ConfigGuiTab getConfigGuiTab()
    {
        return configGuiTab;
    }

    public static void setConfigGuiTab(ConfigGuiTab tab)
    {
        configGuiTab = tab;
    }

    public void init()
    {
        if (DataManager.rebel)
        {
            Configs.Generic.NOTIFYFACTION.setBooleanValue(true);
            this.hostileVisible = 1;
            Configs.Generic.XP5.setBooleanValue(true);
        }
    }

    /**
     * Plays the sound "note.bass" if there are 150+ hostile mobs in the hostile mob count radius
     */
    public void hostileLimit()
    {
        int totalCount = 0;
        for (int i = 0; i < 17; i++)
        {
            totalCount += getCounter().countEntity(i + 22);
        }
        if (this.playSoundCount != 0)
        {
            this.playSoundCount++;
        }
        if (this.sendMsgCount != 0)
        {
            this.sendMsgCount++;
        }
        if (totalCount > 149)
        {
            if (this.playSoundCount == 0)
            {
                SoundEvent soundEvent = Registry.SOUND_EVENT.get(new Identifier(Configs.Generic.SOUNDFILE.getStringValue()));
                if (soundEvent != null)
                {
                    SoundInstance sound = PositionedSoundInstance.master(soundEvent, 1.0F);
                    MinecraftClient.getInstance().getSoundManager().play(sound);
                }
                this.playSoundCount++;
            }
            if (this.playSoundCount > 100)
            {
                this.playSoundCount = -1;
            }
            if (this.sendMsgCount == 0)
            {
                if (Configs.Generic.NOTIFYFACTION.getBooleanValue())
                {
                    MinecraftClient.getInstance().player.sendChatMessage("/ch qm f Automated Message: "	+ totalCount + " mobz. Kill pl0x.");
                }
                if (Configs.Generic.MESSAGE_LIST.getStrings() != null && Configs.Generic.MESSAGE_LIST.getStrings().size() > 0)
                {
                    for (String player : Configs.Generic.MESSAGE_LIST.getStrings())
                    {
                        MinecraftClient.getInstance().player.sendChatMessage("/m " + player	+ " Automated Message: " + totalCount + " mobz. Kill pl0x.");
                    }
                }
                this.sendMsgCount++;
            }
            if (this.sendMsgCount > 10000)
            {
                this.sendMsgCount = -1;
            }
        }
    } // hostileLimit

    public static void load()
    {
        File file = getCurrentStorageFile(true);

        JsonElement element = JsonUtils.parseJsonFile(file);

        if(element != null && element.isJsonObject())
        {

            JsonObject root = element.getAsJsonObject();

            if (JsonUtils.hasString(root, "config_gui_tab"))
            {
                try
                {
                    configGuiTab = ConfigGuiTab.valueOf(root.get("config_gui_tab").getAsString());
                }
                catch (Exception e)
                {}

                if (configGuiTab == null)
                {
                    configGuiTab = ConfigGuiTab.GENERIC;
                }
            }
        }

        canSave = true;
    }

    public static void save()
    {
        save(false);
    }

    public static void save(boolean forceSave)
    {
        if(canSave == false && forceSave == false)
        {
            return;
        }

        JsonObject root = new JsonObject();

        root.add("config_gui_tab", new JsonPrimitive(configGuiTab.name()));

        File file = getCurrentStorageFile(true);
        JsonUtils.writeJsonToFile(root, file);

        canSave = false;
    }

    private static File getCurrentStorageFile(boolean globalData)
    {
        File dir = getCurrentConfigDirectory();

        if(dir.exists() == false && dir.mkdirs() == false)
        {
            MobCountMod.logger.warn("Failed to create the config directory '{}'", dir.getAbsolutePath());
        }

        return new File(dir, getStorageFileName(globalData));
    }

    private static String getStorageFileName(boolean globalData)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        String name = StringUtils.getWorldOrServerName();

        if(name != null)
        {
            if(globalData)
            {
                return Reference.MOD_ID + "_" + name + ".json";
            }
            else
            {
                return Reference.MOD_ID + "_" + name + "_dim" + WorldUtils.getDimensionId(mc.world) + ".json";
            }
        }

        return Reference.MOD_ID + "_default.json";
    }

    public static File getCurrentConfigDirectory()
    {
        return new File(FileUtils.getConfigDirectory(), Reference.MOD_ID);
    }
}