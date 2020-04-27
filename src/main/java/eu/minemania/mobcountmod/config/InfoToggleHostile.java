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

public enum InfoToggleHostile implements IConfigInteger, IHotkeyTogglable
{
    ZOMBIE                ("infoZombie",     "entity.minecraft.zombie",     false, 1,  "", "mcm.description.config.infotoggle", "zombies"),
    BLAZE                 ("infoBlaze",      "entity.minecraft.blaze",      false, 13, "", "mcm.description.config.infotoggle", "blazes"),
    ENDERMAN              ("infoEnderman",   "entity.minecraft.enderman",   false, 15, "", "mcm.description.config.infotoggle", "endermans"),
    SPIDER                ("infoSpider",     "entity.minecraft.spider",     false, 2,  "", "mcm.description.config.infotoggle", "spiders"),
    CREEPER               ("infoCreeper",    "entity.minecraft.creeper",    false, 3,  "", "mcm.description.config.infotoggle", "creepers"),
    WITCH                 ("infoWitch",      "entity.minecraft.witch",      false, 4,  "", "mcm.description.config.infotoggle", "witches"),
    ENDERMITE             ("infoEndermite",  "entity.minecraft.endermite",  false, 5,  "", "mcm.description.config.infotoggle", "endermites"),
    SLIME                 ("infoSlime",      "entity.minecraft.slime",      false, 6,  "", "mcm.description.config.infotoggle", "slimes"),
    GUARDIAN              ("infoGuardian",   "entity.minecraft.guardian",   false, 7,  "", "mcm.description.config.infotoggle", "guardians"),
    ILLAGER               ("infoIllager",    "entity.minecraft.pillager",   false, 8,  "", "mcm.description.config.infotoggle", "illagers"),
    GHAST                 ("infoGhast",      "entity.minecraft.ghast",      false, 14, "", "mcm.description.config.infotoggle", "ghasts"),
    PHANTOM               ("infoPhantom",    "entity.minecraft.phantom",    false, 9,  "", "mcm.description.config.infotoggle", "phantoms"),
    RAVAGER               ("infoRavager",    "entity.minecraft.ravager",    false, 10, "", "mcm.description.config.infotoggle", "ravagers"),
    SHULKER               ("infoShulker",    "entity.minecraft.shulker",    false, 16, "", "mcm.description.config.infotoggle", "shulkers"),
    SILVERFISH            ("infoSilverfish", "entity.minecraft.silverfish", false, 17, "", "mcm.description.config.infotoggle", "silverfishes"),
    SKELETON              ("infoSkeleton",   "entity.minecraft.skeleton",   false, 11, "", "mcm.description.config.infotoggle", "skeletons"),
    VEX                   ("infoVex",        "entity.minecraft.vex",        false, 12, "", "mcm.description.config.infotoggle", "vexes");

    private final String name;
    private final String prettyName;
    private final String comment;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final int defaultLinePosition;
    private boolean valueBoolean;
    private int linePosition;
    private Object[] commentArgs;

    private InfoToggleHostile(String name, String prettyName, boolean defaultValue, int linePosition, String defaultHotkey, String comment, Object... commentArgs)
    {
        this(name, prettyName, defaultValue, linePosition, defaultHotkey, comment, KeybindSettings.DEFAULT, commentArgs);
    }

    private InfoToggleHostile(String name, String prettyName, boolean defaultValue, int linePosition, String defaultHotkey, String comment, KeybindSettings settings, Object... commentArgs)
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
        return InfoToggleHostile.values().length - 1;
    }
}