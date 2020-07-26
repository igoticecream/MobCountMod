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
    BAT("infoBat", false, 10, "", "bats"),
    BEE("infoBee", false, 23, "", "bees"),
    CAT("infoCat", false, 11, "", "cats"),
    CHICKEN("infoChicken", false, 1, "", "chickens"),
    COW("infoCow", false, 4, "", "cows"),
    DONKEY("infoDonkey", false, 24, "", "donkeys"),
    DOLPHIN("infoDolphin", false, 17, "", "dolphins"),
    FISH("infoFish", false, 15, "", "fishes"),
    FOX("infoFox", false, 18, "", "foxes"),
    HORSE("infoHorse", false, 5, "", "horses"),
    IRONGOLEM("infoIronGolem", false, 12, "", "iron golems"),
    LLAMA("infoLlama", false, 25, "", "llamas"),
    MOOSHROOM("infoMooshroom", false, 26, "", "mooshrooms"),
    MULE("infoMule", false, 27, "", "mules"),
    OCELOT("infoOcelot", false, 8, "", "ocelots"),
    PANDA("infoPanda", false, 19, "", "pandas"),
    PARROT("infoParrot", false, 9, "", "parrots"),
    PIG("infoPig", false, 2, "", "pigs"),
    PLAYER("infoPlayer", false, 14, "", "players"),
    POLARBEAR("infoPolarBear", false, 20, "", "polarbears"),
    RABBIT("infoRabbit", false, 6, "", "rabbits"),
    SHEEP("infoSheep", false, 3, "", "sheep"),
    SKELETON_HORSE("infoSkeletonHorse", false, 28, "", "skeleton horses"),
    SNOW_GOLEM("infoSnowGolem", false, 13, "", "snow golems"),
    SQUID("infoSquid", false, 21, "", "squids"),
    STRIDER("infoStrider", false, 32, "", "Striders"),
    TRADER_LLAMA("infoTraderLlama", false, 29, "", "trader llamas"),
    TURTLE("infoTurtle", false, 22, "", "turtles"),
    VILLAGER("infoVillager", false, 16, "", "villagers"),
    WANDERING_TRADER("infoWanderingTrader", false, 30, "", "wandering traders"),
    WOLF("infoWolf", false, 7, "", "wolves"),
    ZOMBIE_HORSE("infoZombieHorse", false, 31, "", "zombie horses");

    private final String name;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final int defaultLinePosition;
    private boolean valueBoolean;
    private int linePosition;
    private Object[] commentArgs;

    InfoTogglePassive(String name, boolean defaultValue, int linePosition, String defaultHotkey, Object... commentArgs)
    {
        this(name, defaultValue, linePosition, defaultHotkey, KeybindSettings.DEFAULT, commentArgs);
    }

    InfoTogglePassive(String name, boolean defaultValue, int linePosition, String defaultHotkey, KeybindSettings settings, Object... commentArgs)
    {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBoolean(this));
        this.linePosition = linePosition;
        this.defaultLinePosition = linePosition;
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
    public String getComment()
    {
        return StringUtils.translate("mcm.description.config.infotoggle", getCommentArgs());
    }

    public Object[] getCommentArgs()
    {
        if (this.commentArgs != null)
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
            if (element.isJsonPrimitive())
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
