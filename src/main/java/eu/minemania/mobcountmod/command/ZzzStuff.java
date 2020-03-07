package eu.minemania.mobcountmod.command;

import static net.minecraft.command.Commands.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import eu.minemania.mobcountmod.counter.DataManager;
import net.minecraft.command.CommandSource;

public class ZzzStuff {
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		ClientCommandManager.addClientSideCommand("zzzStuff");
		LiteralArgumentBuilder<CommandSource> zzzStuff = literal("zzzStuff").executes(ZzzStuff::stuff)
				.then(literal("rubbel").executes(ZzzStuff::rubbel));
		dispatcher.register(zzzStuff);
	}
	
	private static int stuff(CommandContext<CommandSource> context)
	{
		DataManager.getInstance().stuffToggle();
		return 1;
	}
	
	private static int rubbel(CommandContext<CommandSource> context)
	{
		DataManager.getInstance().rubbleToggle();
		return 1;
	}
}