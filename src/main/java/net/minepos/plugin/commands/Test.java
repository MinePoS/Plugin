package net.minepos.plugin.commands;

import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Command {
    public Test() {
        super(CommandsEnum.TEST);
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        sender.sendMessage("test");
    }
}
