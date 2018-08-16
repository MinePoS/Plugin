package net.minepos.plugin.commands;

import com.google.inject.Inject;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import net.minepos.plugin.core.storage.yaml.Lang;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Help extends Command {
    @Inject private Lang lang;

    public Help() {
        super(CommandsEnum.HELP);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        lang.getList("ingame.help").forEach(sender::sendMessage);

        return true;
    }
}
