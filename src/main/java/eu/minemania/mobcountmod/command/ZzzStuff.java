package eu.minemania.mobcountmod.command;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import eu.minemania.mobcountmod.counter.DataManager;
import net.minecraft.server.command.ServerCommandSource;

public class ZzzStuff
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        ClientCommandManager.addClientSideCommand("zzzStuff");
        LiteralArgumentBuilder<ServerCommandSource> zzzStuff = literal("zzzStuff").executes(ZzzStuff::stuff)
                .then(literal("rubbel").executes(ZzzStuff::rubbel));
        dispatcher.register(zzzStuff);
    }

    private static int stuff(CommandContext<ServerCommandSource> context)
    {
        DataManager.getInstance().stuffToggle();
        return 1;
    }

    private static int rubbel(CommandContext<ServerCommandSource> context)
    {
        DataManager.getInstance().rubbleToggle();
        return 1;
    }
}