package net.minepos.plugin.framework;

import lombok.Getter;
import net.minepos.plugin.core.enums.Commands;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Command {
    @Getter private final Commands command;
    @Getter protected boolean playerOnly = false;

    protected Command(Commands command) {
        this.command = command;
    }

    protected abstract boolean execute(CommandSender sender, String[] args);

    public boolean run(CommandSender sender, String[] args) {
        return execute(sender, args);
    }
}
