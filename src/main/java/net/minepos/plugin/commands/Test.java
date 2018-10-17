package net.minepos.plugin.commands;

import com.google.inject.Inject;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.handlers.GUIHandler;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import net.minepos.plugin.core.storage.yaml.Lang;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Command {
    @Inject private Lang lang;
    @Inject private GUIHandler guiHandler;

    public Test() {
        super(CommandsEnum.TEST);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                guiHandler.open("main", player);
                return true;
            }

            if (guiHandler.getGuis().containsKey(args[0])) {
                guiHandler.open(args[0], player);
            } else {
                player.sendMessage(lang.get("ingame.test.unknown-gui", args[0]));
            }
        } else {
            sender.sendMessage(lang.get("ingame.player-only"));
        }

        return true;
    }
}