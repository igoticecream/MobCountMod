package eu.minemania.mobcountmod.event;

import javax.annotation.Nullable;

import eu.minemania.mobcountmod.counter.DataManager;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

public class WorldListener implements IWorldLoadListener
{
    @Override
    public void onWorldLoadPre(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc)
    {
        // Save the settings before the integrated server gets shut down
        if (worldBefore != null)
        {
            DataManager.save();
        }
    }

    @Override
    public void onWorldLoadPost(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc)
    {
        if (worldAfter != null)
        {
            DataManager.load();
        }
    }
}
