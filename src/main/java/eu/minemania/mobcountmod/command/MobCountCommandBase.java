package eu.minemania.mobcountmod.command;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MobCountCommandBase
{
    public static void localOutput(ServerCommandSource sender, String message)
    {
        sendColoredText(sender, Formatting.AQUA, message);
    }

    public static void localOutputT(ServerCommandSource sender, String translationKey, Object... args)
    {
        sendColoredText(sender, Formatting.AQUA, Text.translatable(translationKey, args));
    }

    public static void localError(ServerCommandSource sender, String message)
    {
        sendColoredText(sender, Formatting.DARK_RED, message);
    }

    public static void localErrorT(ServerCommandSource sender, String translationKey, Object... args)
    {
        sendColoredText(sender, Formatting.DARK_RED, Text.translatable(translationKey, args));
    }

    public static void sendColoredText(ServerCommandSource sender, Formatting color, String message)
    {
        sender.getEntity().sendMessage(Text.literal(message).formatted(color));
    }

    public static void sendColoredText(ServerCommandSource sender, Formatting color, MutableText component)
    {
        sender.getEntity().sendMessage(component.formatted(color));
    }
}