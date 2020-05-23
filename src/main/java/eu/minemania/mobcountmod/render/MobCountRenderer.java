package eu.minemania.mobcountmod.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.config.InfoToggleHostile;
import eu.minemania.mobcountmod.config.InfoTogglePassive;
import eu.minemania.mobcountmod.counter.DataManager;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.predicate.entity.EntityPredicates;

public class MobCountRenderer
{
    private static final MobCountRenderer INSTANCE = new MobCountRenderer();
    private int totalPassive = 0;
    private int totalHostile = 0;
    private long infoUpdateTime;
    private final List<StringHolder> lineWrappersHostile = new ArrayList<>();
    private final List<StringHolder> lineWrappersPassive = new ArrayList<>();
    private final List<String> linesHostile = new ArrayList<>();
    private final List<String> linesPassive = new ArrayList<>();

    public static MobCountRenderer getInstance()
    {
        return INSTANCE;
    }

    public static void renderOverlays()
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        DataManager.getInstance().hostileLimit(getInstance().totalHostile);
        if(mc.currentScreen == null && MinecraftClient.isHudEnabled())
        {
            getInstance().renderHUD();
        }
    }

    public void renderHUD()
    {
        long currentTime = System.currentTimeMillis();

        if(currentTime - this.infoUpdateTime >= 50)
        {
            this.updateLines();
            this.infoUpdateTime = currentTime;
        }

        if(DataManager.visibleCounter() == 1)
        {
            DataManager.getCounter().updateBBP();
            RenderUtils.renderText(5, 5, 0xFFAA00, StringUtils.translate("mcm.message.mobcounter.radius", DataManager.getCounter().getRadiusP()));
            RenderUtils.renderText(5, 15, StringUtils.getColor("#E0E0E0", 0), this.linesPassive);
            RenderUtils.renderText(60, 5, getColor(this.totalPassive, true), StringUtils.translate("mcm.message.mobcounter.total", this.totalPassive));
        }

        if(DataManager.visibleHostile() == 1)
        {
            DataManager.getCounter().updateBBH();
            RenderUtils.renderText(125, 5, 0xFFAA00, StringUtils.translate("mcm.message.mobcounter.radius", DataManager.getCounter().getRadiusH()));
            RenderUtils.renderText(125, 15, StringUtils.getColor("#E0E0E0", 0), this.linesHostile);
            RenderUtils.renderText(180, 5, getColor(this.totalHostile, false), StringUtils.translate("mcm.message.mobcounter.total", this.totalHostile));
        }
    }

    private void updateLines()
    {
        this.lineWrappersHostile.clear();
        this.lineWrappersPassive.clear();
        this.totalHostile = 0;
        this.totalPassive = 0;

        List<LinePosPassive> positionsPassive = new ArrayList<LinePosPassive>();
        List<LinePosHostile> positionsHostile = new ArrayList<LinePosHostile>();


        for(InfoTogglePassive toggle : InfoTogglePassive.values())
        {
            if(toggle.getBooleanValue())
            {
                positionsPassive.add(new LinePosPassive(toggle.getIntegerValue(), toggle));
            }
        }

        for(InfoToggleHostile toggle : InfoToggleHostile.values())
        {
            if(toggle.getBooleanValue())
            {
                positionsHostile.add(new LinePosHostile(toggle.getIntegerValue(), toggle));
            }
        }

        Collections.sort(positionsPassive);
        Collections.sort(positionsHostile);

        for(LinePosPassive pos : positionsPassive)
        {
            try
            {
                this.addLinePassive(pos.type);
            }
            catch (Exception e)
            {
                this.addLinePassive(pos.type.getName() + ": exception");
            }
        }

        for(LinePosHostile pos : positionsHostile)
        {
            try
            {
                this.addLineHostile(pos.type);
            }
            catch (Exception e)
            {
                this.addLineHostile(pos.type.getName() + ": exception");
            }
        }

        this.linesHostile.clear();
        this.linesPassive.clear();

        for(StringHolder holder : this.lineWrappersHostile)
        {
            this.linesHostile.add(holder.str);
        }

        for(StringHolder holder : this.lineWrappersPassive)
        {
            this.linesPassive.add(holder.str);
        }
    }

    private int getColor(int amount, boolean passive)
    {
        int color = 0xFFFFFF;
        if(passive && amount > 149)
        {
            color = 0xAA0000;
        }
        else if(!passive)
        {
            if (amount > 149)
            { // if 150+ mobs, display in red.
                color = 0xAA0000;
            }
            else
            {
                DataManager.setPlaySoundCount(100);
            }
        }
        return color;
    }

    private void addLineHostile(String text)
    {
        this.lineWrappersHostile.add(new StringHolder(text));
    }

    private void addLinePassive(String text)
    {
        this.lineWrappersPassive.add(new StringHolder(text));
    }

    private <T extends Entity> String lineText(InfoTogglePassive type, EntityType<T> entity)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int size = mc.world.getEntities(entity, DataManager.getCounter().getPassiveBB(), EntityPredicates.EXCEPT_SPECTATOR).size() - (!type.getPrettyName().equals("Player") ? 0 : (mc.player.isSpectator() ? 0 : 1));
        totalPassive += size;
        return String.format("%s: %s%d%s", type.getPrettyName(), size > Configs.Generic.COUNT_PASSIVE.getIntegerValue() ? GuiBase.TXT_RED : GuiBase.TXT_GREEN, size, GuiBase.TXT_RST);
    }

    private <T extends Entity> String lineText(InfoTogglePassive type, Class<? extends T> entity)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int size = mc.world.getEntities(entity, DataManager.getCounter().getPassiveBB(), EntityPredicates.EXCEPT_SPECTATOR).size() - (!type.getPrettyName().equals("Player") ? 0 : (mc.player.isSpectator() ? 0 : 1));
        totalPassive += size;
        return String.format("%s: %s%d%s", type.getPrettyName(), size > Configs.Generic.COUNT_PASSIVE.getIntegerValue() ? GuiBase.TXT_RED : GuiBase.TXT_GREEN, size, GuiBase.TXT_RST);
    }

    private <T extends Entity> String lineText(InfoToggleHostile type, EntityType<T> entity)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int size = mc.world.getEntities(entity, DataManager.getCounter().getHostileBB(), EntityPredicates.EXCEPT_SPECTATOR).size();
        totalHostile += size;
        return String.format("%s: %s%d%s", type.getPrettyName(), size > Configs.Generic.COUNT_HOSTILE.getIntegerValue() ? GuiBase.TXT_RED : GuiBase.TXT_GREEN, size, GuiBase.TXT_RST);
    }

    private void addLinePassive(InfoTogglePassive type)
    {
        if(type == InfoTogglePassive.BAT)
        {
            this.addLinePassive(lineText(type, EntityType.BAT));
        }
        else if(type == InfoTogglePassive.BEE)
        {
            this.addLinePassive(lineText(type, EntityType.BEE));
        }
        else if(type == InfoTogglePassive.CAT)
        {
            this.addLinePassive(lineText(type, EntityType.CAT));
        }
        else if(type == InfoTogglePassive.CHICKEN)
        {
            this.addLinePassive(lineText(type, EntityType.CHICKEN));
        }
        else if(type == InfoTogglePassive.COW)
        {
            this.addLinePassive(lineText(type, EntityType.COW));
        }
        else if(type == InfoTogglePassive.DONKEY)
        {
            this.addLinePassive(lineText(type, EntityType.DONKEY));
        }
        else if(type == InfoTogglePassive.DOLPHIN)
        {
            this.addLinePassive(lineText(type, EntityType.DOLPHIN));
        }
        else if(type == InfoTogglePassive.FISH)
        {
            this.addLinePassive(lineText(type, FishEntity.class));
        }
        else if(type == InfoTogglePassive.FOX)
        {
            this.addLinePassive(lineText(type, EntityType.FOX));
        }
        else if(type == InfoTogglePassive.HORSE)
        {
            this.addLinePassive(lineText(type, EntityType.HORSE));
        }
        else if(type == InfoTogglePassive.IRONGOLEM)
        {
            this.addLinePassive(lineText(type, EntityType.IRON_GOLEM));
        }
        else if(type == InfoTogglePassive.LLAMA)
        {
            this.addLinePassive(lineText(type, EntityType.LLAMA));
        }
        else if(type == InfoTogglePassive.MOOSHROOM)
        {
            this.addLinePassive(lineText(type, EntityType.MOOSHROOM));
        }
        else if(type == InfoTogglePassive.MULE)
        {
            this.addLinePassive(lineText(type, EntityType.MULE));
        }
        else if(type == InfoTogglePassive.OCELOT)
        {
            this.addLinePassive(lineText(type, EntityType.OCELOT));
        }
        else if(type == InfoTogglePassive.PANDA)
        {
            this.addLinePassive(lineText(type, EntityType.PANDA));
        }
        else if(type == InfoTogglePassive.PARROT)
        {
            this.addLinePassive(lineText(type, EntityType.PARROT));
        }
        else if(type == InfoTogglePassive.PIG)
        {
            this.addLinePassive(lineText(type, EntityType.PIG));
        }
        else if(type == InfoTogglePassive.PLAYER)
        {
            this.addLinePassive(lineText(type, EntityType.PLAYER));
        }
        else if(type == InfoTogglePassive.POLARBEAR)
        {
            this.addLinePassive(lineText(type, EntityType.POLAR_BEAR));
        }
        else if(type == InfoTogglePassive.RABBIT)
        {
            this.addLinePassive(lineText(type, EntityType.RABBIT));
        }
        else if(type == InfoTogglePassive.SHEEP)
        {
            this.addLinePassive(lineText(type, EntityType.SHEEP));
        }
        else if(type == InfoTogglePassive.SKELETON_HORSE)
        {
            this.addLinePassive(lineText(type, EntityType.SKELETON_HORSE));
        }
        else if(type == InfoTogglePassive.SNOW_GOLEM)
        {
            this.addLinePassive(lineText(type, EntityType.SNOW_GOLEM));
        }
        else if(type == InfoTogglePassive.SQUID)
        {
            this.addLinePassive(lineText(type, EntityType.SQUID));
        }
        else if(type == InfoTogglePassive.TRADER_LLAMA)
        {
            this.addLinePassive(lineText(type, EntityType.TRADER_LLAMA));
        }
        else if(type == InfoTogglePassive.TURTLE)
        {
            this.addLinePassive(lineText(type, EntityType.TURTLE));
        }
        else if(type == InfoTogglePassive.VILLAGER)
        {
            this.addLinePassive(lineText(type, EntityType.VILLAGER));
        }
        else if(type == InfoTogglePassive.WANDERING_TRADER)
        {
            this.addLinePassive(lineText(type, EntityType.WANDERING_TRADER));
        }
        else if(type == InfoTogglePassive.WOLF)
        {
            this.addLinePassive(lineText(type, EntityType.WOLF));
        }
        else if(type == InfoTogglePassive.ZOMBIE_HORSE)
        {
            this.addLinePassive(lineText(type, EntityType.ZOMBIE_HORSE));
        }
    }

    private void addLineHostile(InfoToggleHostile type)
    {
        if(type == InfoToggleHostile.BLAZE)
        {
            this.addLineHostile(lineText(type, EntityType.BLAZE));
        }
        else if(type == InfoToggleHostile.CAVE_SPIDER)
        {
            this.addLineHostile(lineText(type, EntityType.CAVE_SPIDER));
        }
        else if(type == InfoToggleHostile.CREEPER)
        {
            this.addLineHostile(lineText(type, EntityType.CREEPER));
        }
        else if(type == InfoToggleHostile.DROWNED)
        {
            this.addLineHostile(lineText(type, EntityType.DROWNED));
        }
        else if(type == InfoToggleHostile.ELDER_GUARDIAN)
        {
            this.addLineHostile(lineText(type, EntityType.ELDER_GUARDIAN));
        }
        else if(type == InfoToggleHostile.ENDERMAN)
        {
            this.addLineHostile(lineText(type, EntityType.ENDERMAN));
        }
        else if(type == InfoToggleHostile.ENDERMITE)
        {
            this.addLineHostile(lineText(type, EntityType.ENDERMITE));
        }
        else if(type == InfoToggleHostile.EVOKER)
        {
            this.addLineHostile(lineText(type, EntityType.EVOKER));
        }
        else if(type == InfoToggleHostile.GHAST)
        {
            this.addLineHostile(lineText(type, EntityType.GHAST));
        }
        else if(type == InfoToggleHostile.GUARDIAN)
        {
            this.addLineHostile(lineText(type, EntityType.GUARDIAN));
        }
        else if(type == InfoToggleHostile.HUSK)
        {
            this.addLineHostile(lineText(type, EntityType.HUSK));
        }
        else if(type == InfoToggleHostile.ILLUSIONER)
        {
            this.addLineHostile(lineText(type, EntityType.ILLUSIONER));
        }
        else if(type == InfoToggleHostile.MAGMA_CUBE)
        {
            this.addLineHostile(lineText(type, EntityType.MAGMA_CUBE));
        }
        else if(type == InfoToggleHostile.PHANTOM)
        {
            this.addLineHostile(lineText(type, EntityType.PHANTOM));
        }
        else if(type == InfoToggleHostile.PILLAGER)
        {
            this.addLineHostile(lineText(type, EntityType.PILLAGER));
        }
        else if(type == InfoToggleHostile.RAVAGER)
        {
            this.addLineHostile(lineText(type, EntityType.RAVAGER));
        }
        else if(type == InfoToggleHostile.SHULKER)
        {
            this.addLineHostile(lineText(type, EntityType.SHULKER));
        }
        else if(type == InfoToggleHostile.SILVERFISH)
        {
            this.addLineHostile(lineText(type, EntityType.SILVERFISH));
        }
        else if(type == InfoToggleHostile.SKELETON)
        {
            this.addLineHostile(lineText(type, EntityType.SKELETON));
        }
        else if(type == InfoToggleHostile.SLIME)
        {
            this.addLineHostile(lineText(type, EntityType.SLIME));
        }
        else if(type == InfoToggleHostile.SPIDER)
        {
            this.addLineHostile(lineText(type, EntityType.SPIDER));
        }
        else if(type == InfoToggleHostile.STRAY)
        {
            this.addLineHostile(lineText(type, EntityType.STRAY));
        }
        else if(type == InfoToggleHostile.VEX)
        {
            this.addLineHostile(lineText(type, EntityType.VEX));
        }
        else if(type == InfoToggleHostile.VINDICATOR)
        {
            this.addLineHostile(lineText(type, EntityType.VINDICATOR));
        }
        else if(type == InfoToggleHostile.WITCH)
        {
            this.addLineHostile(lineText(type, EntityType.WITCH));
        }
        else if(type == InfoToggleHostile.WITHER_SKELETON)
        {
            this.addLineHostile(lineText(type, EntityType.WITHER_SKELETON));
        }
        else if(type == InfoToggleHostile.ZOMBIE)
        {
            this.addLineHostile(lineText(type, EntityType.ZOMBIE));
        }
        else if(type == InfoToggleHostile.ZOMBIE_PIGMAN)
        {
            this.addLineHostile(lineText(type, EntityType.ZOMBIE_PIGMAN));
        }
        else if(type == InfoToggleHostile.ZOMBIE_VILLAGER)
        {
            this.addLineHostile(lineText(type, EntityType.ZOMBIE_VILLAGER));
        }
    }

    private class StringHolder
    {
        public final String str;

        public StringHolder(String str)
        {
            this.str = str;
        }
    }

    private static class LinePosPassive implements Comparable<LinePosPassive>
    {
        private final int position;
        private final InfoTogglePassive type;

        private LinePosPassive(int position, InfoTogglePassive type)
        {
            this.position = position;
            this.type = type;
        }

        @Override
        public int compareTo(LinePosPassive other)
        {
            if(this.position < 0)
            {
                return other.position >= 0 ? 1 : 0;
            }
            else if(other.position < 0 && this.position >= 0)
            {
                return -1;
            }

            return this.position < other.position ? -1 : (this.position > other.position ? 1 : 0);
        }
    }

    private static class LinePosHostile implements Comparable<LinePosHostile>
    {
        private final int position;
        private final InfoToggleHostile type;

        private LinePosHostile(int position, InfoToggleHostile type)
        {
            this.position = position;
            this.type = type;
        }

        @Override
        public int compareTo(LinePosHostile other)
        {
            if(this.position < 0)
            {
                return other.position >= 0 ? 1 : 0;
            }
            else if(other.position < 0 && this.position >= 0)
            {
                return -1;
            }

            return this.position < other.position ? -1 : (this.position > other.position ? 1 : 0);
        }
    }
}