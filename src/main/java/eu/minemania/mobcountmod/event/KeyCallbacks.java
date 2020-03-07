package eu.minemania.mobcountmod.event;

import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.config.Hotkeys;
import eu.minemania.mobcountmod.counter.DataManager;
import eu.minemania.mobcountmod.gui.GuiConfigs;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import net.minecraft.client.Minecraft;

public class KeyCallbacks
{

    public static void init(Minecraft mc)
    {	
        IHotkeyCallback callbackHotkeys = new KeyCallbackHotkeys(mc);
        ValueChangeBooleanCallback valueChangeBooleanCallback = new ValueChangeBooleanCallback();

        Configs.Generic.XP5.setValueChangeCallback(valueChangeBooleanCallback);

        Hotkeys.COUNTER.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.HOSTILE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OPEN_GUI_SETTINGS.getKeybind().setCallback(callbackHotkeys);
    }

    private static class KeyCallbackHotkeys implements IHotkeyCallback
    {
        private final Minecraft mc;

        public KeyCallbackHotkeys(Minecraft mc)
        {
            this.mc = mc;
        }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key)
        {
            if(this.mc.player == null || this.mc.world == null)
            {
                return false;
            }
            if(key == Hotkeys.OPEN_GUI_SETTINGS.getKeybind())
            {
                GuiBase.openGui(new GuiConfigs());
                return true;
            }
            return false;
        }
    }

    private static class ValueChangeBooleanCallback implements IValueChangeCallback<ConfigBoolean>
    {

        public ValueChangeBooleanCallback()
        {
        }

        @Override
        public void onValueChanged(ConfigBoolean config)
        {
            if(config == Configs.Generic.XP5 && DataManager.isStaff())
            {
                DataManager.getCounter().setXP5(Configs.Generic.XP5.getBooleanValue());
            }
        }
    }
}

