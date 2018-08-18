package net.minepos.plugin.commands;

import com.google.inject.Inject;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import net.minepos.plugin.core.objects.gui.*;
import net.minepos.plugin.core.storage.yaml.Lang;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Command {
    @Inject private MFile mFile;
    @Inject private Lang lang;

    public Test() {
        super(CommandsEnum.TEST);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Map<String, FileConfiguration> configs = new HashMap<>();
            Player player = (Player) sender;

            mFile.getItemMaps().keySet().forEach(n -> {
                if (n.toLowerCase().startsWith("gui/")) {
                    configs.put(n.replace("gui/", ""), mFile.getFileConfiguration(n));
                }
            });

            if (args.length == 0 || configs.isEmpty()) {
                ConfigGUIParser.parseGUI(mFile.getFileConfiguration("gui/main"), player).build();
                return true;
            }

            FileConfiguration config = configs.get(args[0]);

            if (config != null) {
                ConfigGUIParser.parseGUI(config, player).build();
            } else {
                player.sendMessage(lang.get("ingame.test.unknown-gui", args[0]));
            }
        } else {
            sender.sendMessage(lang.get("ingame.player-only"));
        }

        return true;
    }
}