package net.minepos.plugin.core.framework;

import net.minepos.plugin.core.objects.enums.CommandsEnum;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public abstract class Command {
    private final CommandsEnum command;

    protected Command() {
        this(null);
    }

    protected Command(CommandsEnum command) {
        this.command = command;
    }

    protected abstract void execute(CommandSender sender, String[] args);

    public void run(CommandSender sender, String[] args) {
        execute(sender, args);
    }

    public CommandsEnum getCommand() {
        return command;
    }
}
