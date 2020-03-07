package eu.minemania.mobcountmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;

import eu.minemania.mobcountmod.Reference;
import eu.minemania.mobcountmod.config.Configs;
import eu.minemania.mobcountmod.counter.DataManager;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;
import java.util.Arrays;
import java.util.Map;

import static net.minecraft.command.Commands.literal;
import static net.minecraft.command.Commands.argument;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;

public class MobCountCommand extends MobCountCommandBase
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        ClientCommandManager.addClientSideCommand("counter");
        LiteralArgumentBuilder<CommandSource> counter = literal("counter").executes(MobCountCommand::info)
                .then(literal("help").executes(MobCountCommand::help))
                .then(literal("message").executes(MobCountCommand::message)
                        .then(literal("clear").executes(MobCountCommand::message_clear)))
                .then(argument("[player1[,player2]]", greedyString()).executes(MobCountCommand::message))
                .then(literal("m").executes(MobCountCommand::message)
                        .then(literal("clear").executes(MobCountCommand::message_clear))
                        .then(argument("[player1[,player2]]", greedyString()).executes(MobCountCommand::message)))
                .then(literal("msg").executes(MobCountCommand::message)
                        .then(literal("clear").executes(MobCountCommand::message_clear))
                        .then(argument("[player1[,player2]]", greedyString()).executes(MobCountCommand::message)))
                .then(literal("sound").executes(MobCountCommand::sound)
                        .then(argument("sound file", word()).executes(MobCountCommand::sound)))
                .then(literal("faction").executes(MobCountCommand::faction)
                        .then(argument("on", bool()).executes(MobCountCommand::faction)))
                .then(literal("xp5").requires(s -> DataManager.isStaff()).executes(MobCountCommand::xp5)
                        .then(argument("on", bool()).executes(MobCountCommand::xp5)));
        dispatcher.register(counter);
    }

    private static int info(CommandContext<CommandSource> context)
    {
        localOutput(context.getSource(), Reference.MOD_NAME + " ["+ Reference.MOD_VERSION+"]");
        localOutput(context.getSource(), "Type /counter help for commands.");
        return 1;
    }

    private static int message(CommandContext<CommandSource> context)
    {
        String message;
        try
        {
            message = getString(context, "[player1[,player2]]");
            Configs.Generic.MESSAGE_LIST.setStrings(Arrays.asList(message.split(",")));
            String toSend = message.replaceAll(",", " ");
            localOutput(context.getSource(), "Now notifying: " + toSend);
        }
        catch (Exception e)
        {
            if(Configs.Generic.MESSAGE_LIST.getStrings() == null)
            {
                localOutput(context.getSource(), "Not currently notifying any players.");
            }
            else
            {
                String toSend = "Currently notifying: ";
                for (String name : Configs.Generic.MESSAGE_LIST.getStrings())
                {
                    toSend += name + " ";
                }
                localOutput(context.getSource(), toSend);
            }
        }
        return 1;
    }

    private static int message_clear(CommandContext<CommandSource> context)
    {
        Configs.Generic.MESSAGE_LIST.setStrings(null);
        localOutput(context.getSource(), "Not currently notifying any players.");
        return 1;
    }

    private static int sound(CommandContext<CommandSource> context)
    {
        String soundFile;
        try
        {
            soundFile = getString(context, "sound file");
            Configs.Generic.SOUNDFILE.setValueFromString(soundFile);
            localOutput(context.getSource(), "Now using " + Configs.Generic.SOUNDFILE.getStringValue() + " as notification sound.");

        }
        catch (Exception e)
        {
            soundFile = Configs.Generic.SOUNDFILE.getStringValue();
            localOutput(context.getSource(), "Current hostile sound: " + Configs.Generic.SOUNDFILE.getStringValue());
        }
        return 1;
    }

    private static int faction(CommandContext<CommandSource> context)
    {
        boolean on;
        try
        {
            on = getBool(context, "on");
            Configs.Generic.NOTIFYFACTION.setBooleanValue(on);
            if(on)
            {
                localOutput(context.getSource(), "Now notifying in faction chat when over 150 mobs.");
            }
            else
            {
                localOutput(context.getSource(), "Not notifying in faction chat.");
            }
        }
        catch (Exception e)
        {
            localOutput(context.getSource(), "NotifyFaction is set to " + Configs.Generic.NOTIFYFACTION.getBooleanValue());
        }
        if(Configs.Generic.NOTIFYFACTION.getBooleanValue())
        {
            localOutput(context.getSource(), "Currently notifying faction chat.");
        }
        else
        {
            localOutput(context.getSource(), "Currently not notifying faction chat.");
        }
        return 1;
    }

    private static int xp5(CommandContext<CommandSource> context)
    {
        boolean on;
        try
        {
            on = getBool(context, "on");
            Configs.Generic.XP5.setBooleanValue(on);
            if(on)
            {
                localOutput(context.getSource(), "Now counting only mobs at ShockerzXP5 kill points... mostly.");
            }
            else
            {
                localOutput(context.getSource(), "Using normal mob counter radius.");
            }
        }
        catch (Exception e)
        {
            localOutput(context.getSource(), "xp5 is set to " + Configs.Generic.XP5.getBooleanValue());
        }
        return 1;
    }

    private static int help(CommandContext<CommandSource> context)
    {
        localOutput(context.getSource(), Reference.MOD_NAME + " ["+ Reference.MOD_VERSION+"] commands");
        int cmdCount = 0;
        CommandDispatcher<CommandSource> dispatcher = Command.commandDispatcher;
        for(CommandNode<CommandSource> command : dispatcher.getRoot().getChildren())
        {
            String cmdName = command.getName();
            if(ClientCommandManager.isClientSideCommand(cmdName))
            {
                Map<CommandNode<CommandSource>, String> usage = dispatcher.getSmartUsage(command, context.getSource());
                for(String u : usage.values())
                {
                    ClientCommandManager.sendFeedback(new TextComponentString("/" + cmdName + " " + u));
                }
                cmdCount += usage.size();
                if(usage.size() == 0)
                {
                    ClientCommandManager.sendFeedback(new TextComponentString("/" + cmdName));
                    cmdCount++;
                }
            }
        }
        return cmdCount;
    }
}