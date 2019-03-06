package net.minepos.plugin.core.managers;

import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.core.enums.Commands;
import net.minepos.plugin.core.storage.file.GCommands;
import net.minepos.plugin.core.utils.text.MessageUtils;
import net.minepos.plugin.core.utils.text.StringUtils;
import net.minepos.plugin.framework.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class CommandManager implements CommandExecutor {
    @Getter private final List<Command> commands = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] badArgs) {
        if (badArgs.length >= 1) {
            String text = String.join(" ", badArgs);
            String commandOnly = "/" + command.getName() + " " + badArgs[0].toLowerCase();

            for (Command cmd : commands) {
                Commands cmds = cmd.getCommand();

                if (StringUtils.startsWith(text, GCommands.getCommands(cmds))) {
                    String[] permissions = GCommands.getPermissions(cmds);

                    if (Arrays.stream(permissions).anyMatch(sender::hasPermission) || permissions.length == 0) {
                        String[] args = text.trim().split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        args = Arrays.copyOfRange(args, 1, args.length);
                        args = args.length == 0 ? new String[]{} : args;

                        if (!cmd.run(sender, args)) {
                            MessageUtils.sendMessage(sender, "ingame.commands.incorrect-usage", commandOnly + " " + GCommands.getUsage(cmds));
                        }
                    } else {
                        MessageUtils.sendMessage(sender, "ingame.commands.no-permission");
                    }

                    return true;
                }
            }

            MessageUtils.sendMessage(sender, "ingame.commands.unknown-command", commandOnly);
        }

        return true;
    }
}
