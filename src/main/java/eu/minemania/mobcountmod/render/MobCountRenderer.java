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
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;

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

    private <T extends Entity> String lineText(InfoTogglePassive type, Class<? extends T> entity)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int size = mc.world.getNonSpectatingEntities(entity, DataManager.getCounter().getPassiveBB()).size() - (!type.getPrettyName().equals("Player") ? 0 : (mc.player.isSpectator() ? 0 : 1));
        totalPassive += size;
        return String.format("%s: %s%d%s", type.getPrettyName(), size > Configs.Generic.COUNT_PASSIVE.getIntegerValue() ? GuiBase.TXT_RED : GuiBase.TXT_GREEN, size, GuiBase.TXT_RST);
    }

    private <T extends Entity> String lineText(InfoToggleHostile type, Class<? extends T> entity)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        int size = mc.world.getNonSpectatingEntities(entity, DataManager.getCounter().getHostileBB()).size();
        totalHostile += size;
        return String.format("%s: %s%d%s", type.getPrettyName(), size > Configs.Generic.COUNT_HOSTILE.getIntegerValue() ? GuiBase.TXT_RED : GuiBase.TXT_GREEN, size, GuiBase.TXT_RST);
    }

    private void addLinePassive(InfoTogglePassive type)
    {
        if(type == InfoTogglePassive.BAT)
        {
            this.addLinePassive(lineText(type, BatEntity.class));
        }
        else if(type == InfoTogglePassive.BEE)
        {
            this.addLinePassive(lineText(type, BeeEntity.class));
        }
        else if(type == InfoTogglePassive.CAT)
        {
            this.addLinePassive(lineText(type, CatEntity.class));
        }
        else if(type == InfoTogglePassive.CHICKEN)
        {
            this.addLinePassive(lineText(type, ChickenEntity.class));
        }
        else if(type == InfoTogglePassive.COW)
        {
            this.addLinePassive(lineText(type, CowEntity.class));
        }
        else if(type == InfoTogglePassive.DOLPHIN)
        {
            this.addLinePassive(lineText(type, DolphinEntity.class));
        }
        else if(type == InfoTogglePassive.FISH)
        {
            this.addLinePassive(lineText(type, FishEntity.class));
        }
        else if(type == InfoTogglePassive.FOX)
        {
            this.addLinePassive(lineText(type, FoxEntity.class));
        }
        else if(type == InfoTogglePassive.HORSE)
        {
            this.addLinePassive(lineText(type, HorseBaseEntity.class));
        }
        else if(type == InfoTogglePassive.IRONGOLEM)
        {
            this.addLinePassive(lineText(type, IronGolemEntity.class));
        }
        else if(type == InfoTogglePassive.OCELOT)
        {
            this.addLinePassive(lineText(type, OcelotEntity.class));
        }
        else if(type == InfoTogglePassive.PANDA)
        {
            this.addLinePassive(lineText(type, PandaEntity.class));
        }
        else if(type == InfoTogglePassive.PARROT)
        {
            this.addLinePassive(lineText(type, ParrotEntity.class));
        }
        else if(type == InfoTogglePassive.PIG)
        {
            this.addLinePassive(lineText(type, PigEntity.class));
        }
        else if(type == InfoTogglePassive.PLAYER)
        {
            this.addLinePassive(lineText(type, PlayerEntity.class));
        }
        else if(type == InfoTogglePassive.POLARBEAR)
        {
            this.addLinePassive(lineText(type, PolarBearEntity.class));
        }
        else if(type == InfoTogglePassive.RABBIT)
        {
            this.addLinePassive(lineText(type, RabbitEntity.class));
        }
        else if(type == InfoTogglePassive.SHEEP)
        {
            this.addLinePassive(lineText(type, SheepEntity.class));
        }
        else if(type == InfoTogglePassive.SNOWGOLEM)
        {
            this.addLinePassive(lineText(type, SnowGolemEntity.class));
        }
        else if(type == InfoTogglePassive.SQUID)
        {
            this.addLinePassive(lineText(type, SquidEntity.class));
        }
        else if(type == InfoTogglePassive.TRADER)
        {
            this.addLinePassive(lineText(type, AbstractTraderEntity.class));
        }
        else if(type == InfoTogglePassive.TURTLE)
        {
            this.addLinePassive(lineText(type, TurtleEntity.class));
        }
        else if(type == InfoTogglePassive.WOLF)
        {
            this.addLinePassive(lineText(type, WolfEntity.class));
        }
    }

    private void addLineHostile(InfoToggleHostile type)
    {
        if(type == InfoToggleHostile.BLAZE)
        {
            this.addLineHostile(lineText(type, BlazeEntity.class));
        }
        else if(type == InfoToggleHostile.CREEPER)
        {
            this.addLineHostile(lineText(type, CreeperEntity.class));
        }
        else if(type == InfoToggleHostile.ENDERMAN)
        {
            this.addLineHostile(lineText(type, EndermanEntity.class));
        }
        else if(type == InfoToggleHostile.ENDERMITE)
        {
            this.addLineHostile(lineText(type, EndermiteEntity.class));
        }
        else if(type == InfoToggleHostile.GHAST)
        {
            this.addLineHostile(lineText(type, GhastEntity.class));
        }
        else if(type == InfoToggleHostile.GUARDIAN)
        {
            this.addLineHostile(lineText(type, GuardianEntity.class));
        }
        else if(type == InfoToggleHostile.ILLAGER)
        {
            this.addLineHostile(lineText(type, IllagerEntity.class));
        }
        else if(type == InfoToggleHostile.PHANTOM)
        {
            this.addLineHostile(lineText(type, PhantomEntity.class));
        }
        else if(type == InfoToggleHostile.RAVAGER)
        {
            this.addLineHostile(lineText(type, RavagerEntity.class));
        }
        else if(type == InfoToggleHostile.SHULKER)
        {
            this.addLineHostile(lineText(type, ShulkerEntity.class));
        }
        else if(type == InfoToggleHostile.SILVERFISH)
        {
            this.addLineHostile(lineText(type, SilverfishEntity.class));
        }
        else if(type == InfoToggleHostile.SKELETON)
        {
            this.addLineHostile(lineText(type, AbstractSkeletonEntity.class));
        }
        else if(type == InfoToggleHostile.SLIME)
        {
            this.addLineHostile(lineText(type, SlimeEntity.class));
        }
        else if(type == InfoToggleHostile.SPIDER)
        {
            this.addLineHostile(lineText(type, SpiderEntity.class));
        }
        else if(type == InfoToggleHostile.VEX)
        {
            this.addLineHostile(lineText(type, VexEntity.class));
        }
        else if(type == InfoToggleHostile.WITCH)
        {
            this.addLineHostile(lineText(type, WitchEntity.class));
        }
        else if(type == InfoToggleHostile.ZOMBIE)
        {
            this.addLineHostile(lineText(type, ZombieEntity.class));
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