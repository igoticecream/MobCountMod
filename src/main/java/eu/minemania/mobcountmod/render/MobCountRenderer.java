package eu.minemania.mobcountmod.render;

import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.counter.DataManager;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.Minecraft;

public class MobCountRenderer
{
    private static final MobCountRenderer INSTANCE = new MobCountRenderer();
    private int totalPassive = 0;

    public static MobCountRenderer getInstance()
    {
        return INSTANCE;
    }

    public static void renderOverlays()
    {
        Minecraft mc = Minecraft.getInstance();
        DataManager.getInstance().hostileLimit();
        if(mc.currentScreen == null && Minecraft.isGuiEnabled())
        {
            if(DataManager.visibleCounter() > 0)
            {
                MobCountRenderer.getInstance().displayPassiveCompact();
                if(DataManager.visibleCounter() > 1)
                {
                    MobCountRenderer.getInstance().displayPassiveExpanded();
                }
            }

            if(DataManager.visibleHostile() > 0)
            {
                MobCountRenderer.getInstance().displayHostile();
            }
        }
    }

    /**
     * Display compact passive
     */
    public void displayPassiveCompact()
    {
        DataManager.getCounter().updateBB();
        RenderUtils.renderText(5, 5, 0xFFAA00, "Radius: " + DataManager.getCounter().getRadius());
        totalPassive = 0;
        if (DataManager.isStaff())
        {
            RenderUtils.renderText(180, 5, 0xFFFFFF, "Players: " + DataManager.getCounter().countEntity(12));
            int color = 0xFFFFFF;
            int count = DataManager.getCounter().countEntity(11);
            if (count > 16)
            {
                color = 0xAA0000;
            }
            RenderUtils.renderText(180, 15, color, "Snowmen: " + count);
        }
        String toDisplay;

        int color = 0xFFFFFF;
        for (int i = 0; i < 10; i++)
        {
            int count = DataManager.getCounter().countEntity(i);
            totalPassive += count;
            toDisplay = "" + count;
            if (count > 16)
            {
                color = 0xAA0000;
            }
            RenderUtils.renderText(5, i * 10 + 15, color, DataManager.getPassive(i) + toDisplay);
            color = 0xFFFFFF;
        }
        if(DataManager.visibleCounter() < 2)
        {
            color = 0xFFFFFF;
            if (totalPassive > 149)
            { // if 150+ mobs, display in red.
                color = 0xAA0000;
            }
            RenderUtils.renderText(95, 5, color, "Total: " + totalPassive);
        }
    }

    /**
     * Display expanded passive
     */
    public void displayPassiveExpanded()
    {
        for (int i = 10; i < 19; i++)
        {
            int color = 0xFFFFFF;
            int count = DataManager.getCounter().countEntity(i);
            totalPassive += count;
            String toDisplay = "" + count;

            if (count > 16)
            {
                color = 0xAA0000;
            }
            RenderUtils.renderText(95, i * 10 - 95, color, DataManager.getPassive(i) + toDisplay);
            color = 0xFFFFFF;
        }

        if (DataManager.isStaff())
        {
            int color = 0xFFFFFF;
            int count = DataManager.getCounter().countEntity(10);
            if (count > 16)
            {
                color = 0xAA0000;
            }
            RenderUtils.renderText(180, 25, color, "Golems: " + count);
        }
        int color = 0xFFFFFF;
        if (totalPassive > 149)
        { // if 150+ mobs, display in red.
            color = 0xAA0000;
        }
        RenderUtils.renderText(95, 5, color, "Total: " + totalPassive);
    }

    /**
     * Display hostile counter
     */
    public void displayHostile()
    {
        int offset = 5;
        if (DataManager.visibleCounter() > 0)
        {
            offset += 130;
        }
        if (DataManager.isStaff())
        {
            offset += 10;
        }
        if (Configs.Generic.XP5.getBooleanValue())
        {
            RenderUtils.renderText(5, offset, 0xFFAA00, "ShockerzXP5");
        }
        else
        {
            DataManager.getCounter().updateHostileBB();
            RenderUtils.renderText(5, offset, 0xFFAA00, "Radius: " + DataManager.getCounter().getHRadius());
        }

        int totalCount = 0;
        for (int i = 0; i < 8; i++)
        {
            int count = DataManager.getCounter().countEntity(i + 19);
            totalCount += count;
            RenderUtils.renderText(5, i * 10 + 10 + offset, 0xFFFFFF, DataManager.getHostile(i) + count);
        }
        for (int i = 8; i < 16; i++)
        {
            int count = DataManager.getCounter().countEntity(i + 19);
            totalCount += count;
            if (DataManager.visibleHostile() > 1)
            {
                RenderUtils.renderText(95, i * 10 - 80 + offset, 0xFFFFFF, DataManager.getHostile(i) + count);
            }
        }
        int color = 0xFFFFFF;
        if (totalCount > 149)
        { // if 150+ mobs, display in red.
            color = 0xAA0000;
        }
        else
        {
            DataManager.setPlaySoundCount(100);
        }

        RenderUtils.renderText(95, offset, color, "Total: " + totalCount);
    }
}