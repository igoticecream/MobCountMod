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
    BLAZE                 ("infoBlaze",          "entity.minecraft.blaze",          false, 13, "", "mcm.description.config.infotoggle", "blazes"),
    CAVE_SPIDER           ("infoCaveSpider",     "entity.minecraft.cave_spider",    false, 18, "", "mcm.description.config.infotoggle", "cave spiders"),
    CREEPER               ("infoCreeper",        "entity.minecraft.creeper",        false, 3,  "", "mcm.description.config.infotoggle", "creepers"),
    DROWNED               ("infoDrowned",        "entity.minecraft.drowned",        false, 19, "", "mcm.description.config.infotoggle", "drowneds"),
    ELDER_GUARDIAN        ("infoElderGuardian",  "entity.minecraft.elder_guardian", false, 20, "", "mcm.description.config.infotoggle", "elder guardians"),
    ENDERMAN              ("infoEnderman",       "entity.minecraft.enderman",       false, 15, "", "mcm.description.config.infotoggle", "endermans"),
    ENDERMITE             ("infoEndermite",      "entity.minecraft.endermite",      false, 5,  "", "mcm.description.config.infotoggle", "endermites"),
    EVOKER                ("infoEvoker",         "entity.minecraft.evoker",         false, 21, "", "mcm.description.config.infotoggle", "evokers"),
    GHAST                 ("infoGhast",          "entity.minecraft.ghast",          false, 14, "", "mcm.description.config.infotoggle", "ghasts"),
    GUARDIAN              ("infoGuardian",       "entity.minecraft.guardian",       false, 7,  "", "mcm.description.config.infotoggle", "guardians"),
    HUSK                  ("infoHusk",           "entity.minecraft.husk",           false, 22, "", "mcm.description.config.infotoggle", "husks"),
    ILLUSIONER            ("infoIllusioner",     "entity.minecraft.illusioner",     false, 23, "", "mcm.description.config.infotoggle", "illusioners"),
    MAGMA_CUBE            ("infoMagmaCube",      "entity.minecraft.magma_cube",     false, 24, "", "mcm.description.config.infotoggle", "magma cubes"),
    PHANTOM               ("infoPhantom",        "entity.minecraft.phantom",        false, 9,  "", "mcm.description.config.infotoggle", "phantoms"),
    PILLAGER              ("infoPillager",       "entity.minecraft.pillager",       false, 8,  "", "mcm.description.config.infotoggle", "pillagers"),
    RAVAGER               ("infoRavager",        "entity.minecraft.ravager",        false, 10, "", "mcm.description.config.infotoggle", "ravagers"),
    SHULKER               ("infoShulker",        "entity.minecraft.shulker",        false, 16, "", "mcm.description.config.infotoggle", "shulkers"),
    SILVERFISH            ("infoSilverfish",     "entity.minecraft.silverfish",     false, 17, "", "mcm.description.config.infotoggle", "silverfishes"),
    SKELETON              ("infoSkeleton",       "entity.minecraft.skeleton",       false, 11, "", "mcm.description.config.infotoggle", "skeletons"),
    SLIME                 ("infoSlime",          "entity.minecraft.slime",          false, 6,  "", "mcm.description.config.infotoggle", "slimes"),
    SPIDER                ("infoSpider",         "entity.minecraft.spider",         false, 2,  "", "mcm.description.config.infotoggle", "spiders"),
    STRAY                 ("infoStray",          "entity.minecraft.stray",          false, 25, "", "mcm.description.config.infotoggle", "strays"),
    VEX                   ("infoVex",            "entity.minecraft.vex",            false, 12, "", "mcm.description.config.infotoggle", "vexes"),
    VINDICATOR            ("infoVindicator",     "entity.minecraft.vindicator",     false, 26, "", "mcm.description.config.infotoggle", "vindicators"),
    WITCH                 ("infoWitch",          "entity.minecraft.witch",          false, 4,  "", "mcm.description.config.infotoggle", "witches"),
    WITHER_SKELETON       ("infoWitherSkeleton", "entity.minecraft.wither_skeleton",false, 27, "", "mcm.description.config.infotoggle", "wither skeletons"),
    ZOMBIE                ("infoZombie",         "entity.minecraft.zombie",         false, 1,  "", "mcm.description.config.infotoggle", "zombies"),
    ZOMBIE_PIGMAN         ("infoZombiePigman",   "entity.minecraft.zombie_pigman",  false, 28, "", "mcm.description.config.infotoggle", "zombie pigmans"),
    ZOMBIE_VILLAGER       ("infoZombieVillager", "entity.minecraft.zombie_villager",false, 29, "", "mcm.description.config.infotoggle", "zombie villagers");

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