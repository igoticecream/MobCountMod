package eu.minemania.mobcountmod.event;

import eu.minemania.mobcountmod.Reference;
import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.config.Hotkeys;
import eu.minemania.mobcountmod.counter.DataManager;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;
import fi.dy.masa.malilib.util.KeyCodes;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {

	private static final InputHandler INSTANCE = new InputHandler();

	private InputHandler() {
	}

	public static InputHandler getInstance() {
		return INSTANCE;
	}

	@Override
	public void addKeysToMap(IKeybindManager manager) {
		for(IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
			manager.addKeybindToMap(hotkey.getKeybind());
		}

	}

	@Override
	public void addHotkeys(IKeybindManager manager) {
		manager.addHotkeysForCategory(Reference.MOD_NAME, "mcm.hotkeys.category.generic_hotkeys", Hotkeys.HOTKEY_LIST);
	}
	
	@Override
	public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState) {
		if (eventKeyState) {
			if(Configs.Generic.ENABLED.getBooleanValue()) {
				if (Hotkeys.COUNTER.getKeybind().isKeybindHeld()) {
					if(keyCode == KeyCodes.KEY_UP) {
						DataManager.getCounter().increaseRadius(DataManager.isStaff());
					} else if(keyCode == KeyCodes.KEY_DOWN) {
						DataManager.getCounter().decreaseRadius();
					} else {
						DataManager.upVisibleCounter();
						if(DataManager.visibleCounter() > 2) {
							DataManager.resetVisibleCounter();
						}
					}
					return true;
				}
				if (Hotkeys.HOSTILE.getKeybind().isKeybindHeld()) {
					if(keyCode == KeyCodes.KEY_UP) {
						DataManager.getCounter().increaseHRadius(DataManager.isStaff());
					} else if(keyCode == KeyCodes.KEY_DOWN) {
						DataManager.getCounter().decreaseHRadius();
					} else {
						DataManager.upVisibleHostile();
						if(DataManager.visibleHostile() > 2) {
							DataManager.resetVisibleHostile();
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}