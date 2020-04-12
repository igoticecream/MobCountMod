package eu.minemania.mobcountmod.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import eu.minemania.mobcountmod.MobCountMod;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigInteger;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBoolean;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public enum InfoTogglePassive implements IConfigInteger, IHotkeyTogglable
{
    CHICKEN         ("infoChicken",         false, 1,  "", "Show the amount of chickens"),
    PIG             ("infoPig",             false, 2,  "", "Show the amount of pigs"),
    SHEEP           ("infoSheep",           false, 3,  "", "Show the amount of sheep"),
    COW             ("infoCow",             false, 4,  "", "Show the amount of cows"),
    HORSE           ("infoHorse",           false, 5,  "", "Show the amount of horses"),
    RABBIT          ("infoRabbit",          false, 6,  "", "Show the amount of rabbits"),
    WOLF            ("infoWolf",            false, 7,  "", "Show the amount of wolves"),
    OCELOT          ("infoOcelot",          false, 8,  "", "Show the amount of ocelots"),
    PARROT          ("infoParrot",          false, 9,  "", "Show the amount of parrots"),
    BAT             ("infoBat",             false, 10, "", "Show the amount of bats"),
    CAT             ("infoCat",             false, 11, "", "Show the amount of cats"),
    IRONGOLEM       ("infoIronGolem",       false, 12, "", "Show the amount of iron golems"),
    SNOWGOLEM       ("infoSnowGolem",       false, 13, "", "Show the amount of snow golems"),
    PLAYER          ("infoPlayer",          false, 14, "", "Show the amount of players"),
    FISH            ("infoFish",            false, 15, "", "Show the amount of fishes"),
    TRADER          ("infoTrader",          false, 16, "", "Show the amount of traders"),
    DOLPHIN         ("infoDolphin",         false, 17, "", "Show the amount of dolphins"),
    FOX             ("infoFox",             false, 18, "", "Show the amount of foxes"),
    PANDA           ("infoPanda",           false, 19, "", "Show the amount of pandas"),
    POLARBEAR       ("infoPolarBear",       false, 20, "", "Show the amount of polarbears"),
    SQUID           ("infoSquid",           false, 21, "", "Show the amount of squids"),
    TURTLE          ("infoTurtle",          false, 22, "", "Show the amount of turtles"),
    BEE             ("infoBee",             false, 23, "", "Show the amount of bees");

    private final String name;
    private final String prettyName;
    private final String comment;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final int defaultLinePosition;
    private boolean valueBoolean;
    private int linePosition;

    private InfoTogglePassive(String name, boolean defaultValue, int linePosition, String defaultHotkey, String comment)
    {
        this(name, defaultValue, linePosition, defaultHotkey, comment, KeybindSettings.DEFAULT);
    }

    private InfoTogglePassive(String name, boolean defaultValue, int linePosition, String defaultHotkey, String comment, KeybindSettings settings)
    {
        this.name = name;
        this.prettyName = name.replace("info", "");
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBoolean(this));
        this.linePosition = linePosition;
        this.defaultLinePosition = linePosition;
        this.comment = comment;
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.HOTKEY;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getPrettyName()
    {
        return this.prettyName;
    }

    @Override
    public String getComment()
    {
        return comment != null ? this.comment : "";
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if(element.isJsonPrimitive())
            {
                this.valueBoolean = element.getAsBoolean();
            }
            else
            {
                MobCountMod.logger.warn("Failed to read config value for '{}' from the JSON config", this.getName());
            }
        }
        catch (Exception e)
        {
            MobCountMod.logger.warn("Failed to read config value for '{}' from the JSON config", this.getName(), e);
        }
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(this.valueBoolean);
    }

    @Override
    public boolean isModified()
    {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public void resetToDefault()
    {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public String getDefaultStringValue()
    {
        return String.valueOf(this.defaultValueBoolean);
    }

    @Override
    public void setValueFromString(String value)
    {
        try
        {
            this.valueBoolean = Boolean.parseBoolean(value);
        }
        catch (Exception e)
        {
            MobCountMod.logger.warn("Failed to red config value for '{}' from the JSON config", this.getName(), e);
        }
    }

    @Override
    public boolean isModified(String newValue)
    {
        return String.valueOf(this.defaultValueBoolean).equals(newValue) == false;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public boolean getBooleanValue()
    {
        return this.valueBoolean;
    }

    @Override
    public boolean getDefaultBooleanValue()
    {
        return this.defaultValueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value)
    {
        this.valueBoolean = value;
    }

    @Override
    public IKeybind getKeybind()
    {
        return this.keybind;
    }

    @Override
    public int getIntegerValue()
    {
        return this.linePosition;
    }

    @Override
    public int getDefaultIntegerValue()
    {
        return this.defaultLinePosition;
    }

    @Override
    public void setIntegerValue(int value)
    {
        this.linePosition = value;
    }

    @Override
    public int getMinIntegerValue()
    {
        return 0;
    }

    @Override
    public int getMaxIntegerValue()
    {
        return InfoTogglePassive.values().length - 1;
    }
}
