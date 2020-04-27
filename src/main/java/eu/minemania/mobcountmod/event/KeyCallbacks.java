package eu.minemania.mobcountmod.event;

import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.config.Hotkeys;
import eu.minemania.mobcountmod.counter.DataManager;
import eu.minemania.mobcountmod.gui.GuiConfigs;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import net.minecraft.client.MinecraftClient;

public class KeyCallbacks
{
    public static void init(MinecraftClient mc)
    {	
        IHotkeyCallback callbackHotkeys = new KeyCallbackHotkeys(mc);
        ValueChangeBooleanCallback valueChangeBooleanCallback = new ValueChangeBooleanCallback();
        ValueChangeIntegerCallback valueChangeIntegerCallback = new ValueChangeIntegerCallback();

        Configs.Generic.RADIUS_HOSTILE.setValueChangeCallback(valueChangeIntegerCallback);
        Configs.Generic.RADIUS_PASSIVE.setValueChangeCallback(valueChangeIntegerCallback);
        Configs.Generic.XP5.setValueChangeCallback(valueChangeBooleanCallback);

        Hotkeys.PASSIVE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.HOSTILE.getKeybind().setCallback(callbackHotkeys);
        Hotkeys.OPEN_GUI_SETTINGS.getKeybind().setCallback(callbackHotkeys);
    }

    private static class KeyCallbackHotkeys implements IHotkeyCallback
    {
        private final MinecraftClient mc;

        public KeyCallbackHotkeys(MinecraftClient mc)
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
            if(config == Configs.Generic.XP5)
            {
                DataManager.getCounter().setXP5(config.getBooleanValue());
            }
        }
    }

    private static class ValueChangeIntegerCallback implements IValueChangeCallback<ConfigInteger>
    {

        public ValueChangeIntegerCallback()
        {
        }

        @Override
        public void onValueChanged(ConfigInteger config)
        {
            if(config == Configs.Generic.RADIUS_HOSTILE)
            {
                DataManager.getCounter().setRadius(config.getIntegerValue(), false);
            }
            else if(config == Configs.Generic.RADIUS_PASSIVE)
            {
                DataManager.getCounter().setRadius(config.getIntegerValue(), true);
            }
        }
    }
}