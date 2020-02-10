package eu.minemania.mobcountmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import eu.minemania.mobcountmod.interfaces.ITextFieldWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

@Mixin(TextFieldWidget.class)
public abstract class MixinTextFieldWidget implements ITextFieldWidget {
	@Accessor("maxLength")
	@Override
	public abstract int clientcommands_getMaxLengthMCM();
}
