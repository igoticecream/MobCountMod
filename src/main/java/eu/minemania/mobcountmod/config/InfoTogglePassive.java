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
import fi.dy.masa.malilib.util.StringUtils;

public enum InfoTogglePassive implements IConfigInteger, IHotkeyTogglable
{
    CHICKEN         ("infoChicken",   "entity.minecraft.chicken",       false, 1,  "", "mcm.description.config.infotoggle", "chickens"),
    PIG             ("infoPig",       "entity.minecraft.pig",           false, 2,  "", "mcm.description.config.infotoggle", "pigs"),
    SHEEP           ("infoSheep",     "entity.minecraft.sheep",         false, 3,  "", "mcm.description.config.infotoggle", "sheep"),
    COW             ("infoCow",       "entity.minecraft.cow",           false, 4,  "", "mcm.description.config.infotoggle", "cows"),
    HORSE           ("infoHorse",     "entity.minecraft.horse",         false, 5,  "", "mcm.description.config.infotoggle", "horses"),
    RABBIT          ("infoRabbit",    "entity.minecraft.rabbit",        false, 6,  "", "mcm.description.config.infotoggle", "rabbits"),
    WOLF            ("infoWolf",      "entity.minecraft.wolf",          false, 7,  "", "mcm.description.config.infotoggle", "wolves"),
    OCELOT          ("infoOcelot",    "entity.minecraft.ocelot",        false, 8,  "", "mcm.description.config.infotoggle", "ocelots"),
    PARROT          ("infoParrot",    "entity.minecraft.parrot",        false, 9,  "", "mcm.description.config.infotoggle", "parrots"),
    BAT             ("infoBat",       "entity.minecraft.bat",           false, 10, "", "mcm.description.config.infotoggle", "bats"),
    CAT             ("infoCat",       "entity.minecraft.cat",           false, 11, "", "mcm.description.config.infotoggle", "cats"),
    IRONGOLEM       ("infoIronGolem", "entity.minecraft.iron_golem",    false, 12, "", "mcm.description.config.infotoggle", "iron golems"),
    SNOWGOLEM       ("infoSnowGolem", "entity.minecraft.snow_golem",    false, 13, "", "mcm.description.config.infotoggle", "snow golems"),
    PLAYER          ("infoPlayer",    "entity.minecraft.player",        false, 14, "", "mcm.description.config.infotoggle", "players"),
    FISH            ("infoFish",      "entity.minecraft.tropical_fish", false, 15, "", "mcm.description.config.infotoggle", "fishes"),
    TRADER          ("infoTrader",    "entity.minecraft.villager",      false, 16, "", "mcm.description.config.infotoggle", "traders"),
    DOLPHIN         ("infoDolphin",   "entity.minecraft.dolphin",       false, 17, "", "mcm.description.config.infotoggle", "dolphins"),
    FOX             ("infoFox",       "entity.minecraft.fox",           false, 18, "", "mcm.description.config.infotoggle", "foxes"),
    PANDA           ("infoPanda",     "entity.minecraft.panda",         false, 19, "", "mcm.description.config.infotoggle", "pandas"),
    POLARBEAR       ("infoPolarBear", "entity.minecraft.polar_bear",    false, 20, "", "mcm.description.config.infotoggle", "polarbears"),
    SQUID           ("infoSquid",     "entity.minecraft.squid",         false, 21, "", "mcm.description.config.infotoggle", "squids"),
    TURTLE          ("infoTurtle",    "entity.minecraft.turtle",        false, 22, "", "mcm.description.config.infotoggle", "turtles"),
    BEE             ("infoBee",       "entity.minecraft.bee",           false, 23, "", "mcm.description.config.infotoggle", "bees");

    private final String name;
    private final String prettyName;
    private final String comment;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final int defaultLinePosition;
    private boolean valueBoolean;
    private int linePosition;
    private Object[] commentArgs;

    private InfoTogglePassive(String name, String prettyName, boolean defaultValue, int linePosition, String defaultHotkey, String comment, Object... commentArgs)
    {
        this(name, prettyName, defaultValue, linePosition, defaultHotkey, comment, KeybindSettings.DEFAULT, commentArgs);
    }

    private InfoTogglePassive(String name, String prettyName, boolean defaultValue, int linePosition, String defaultHotkey, String comment, KeybindSettings settings, Object... commentArgs)
    {
        this.name = name;
        this.prettyName = prettyName;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBoolean(this));
        this.linePosition = linePosition;
        this.defaultLinePosition = linePosition;
        this.comment = comment;
        this.commentArgs = commentArgs;
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
        return StringUtils.translate(this.prettyName);
    }

    @Override
    public String getComment()
    {
        return comment != null ? StringUtils.translate(this.comment, getCommentArgs()) : "";
    }

    public Object[] getCommentArgs()
    {
        if(this.commentArgs != null)
        {
            return this.commentArgs;
        }
        return new Object[0];
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
