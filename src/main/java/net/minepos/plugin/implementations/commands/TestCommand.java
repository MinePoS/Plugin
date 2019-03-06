package net.minepos.plugin.implementations.commands;

import net.minepos.plugin.core.enums.Commands;
import net.minepos.plugin.framework.Command;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    public TestCommand() {
        super(Commands.TEST);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("test");

        return true;
    }
}
