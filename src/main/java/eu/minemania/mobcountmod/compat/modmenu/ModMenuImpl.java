package eu.minemania.mobcountmod.compat.modmenu;

import java.util.function.Function;

import eu.minemania.mobcountmod.Reference;
import eu.minemania.mobcountmod.gui.GuiConfigs;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuImpl implements ModMenuApi
{
    @Override
    public String getModId()
    {
        return Reference.MOD_ID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory()
    {
        return (screen) -> {
            GuiConfigs gui = new GuiConfigs();
            gui.setParent(screen);
            return gui;
        };
    }
}
