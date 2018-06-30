package net.minepos.plugin.core.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.storage.yaml.Commands;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class CommandHandler implements CommandExecutor {
    @Inject private Commands commands;

    private List<Command> commandsList;

    public CommandHandler() {
        commandsList = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commandsList;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] badArgs) {
        if (badArgs.length >= 1) {
            for (Command cmd : commandsList) {
                String msg = StringUtils.arrayToString(badArgs, " ");
                String name = commands.getCommand(cmd.getCommand());
                String[] args = msg.toLowerCase().replace(name.toLowerCase(), "").trim().split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (StringUtils.startsWith(msg, name)) {
                    cmd.run(sender, args[0].isEmpty() ? new String[]{} : args);
                }
            }
        }

        return true;
    }
}
