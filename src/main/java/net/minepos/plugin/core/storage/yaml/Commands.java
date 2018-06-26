package net.minepos.plugin.core.storage.yaml;

import com.google.inject.Inject;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import org.bukkit.configuration.file.FileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Commands {
    @Inject private MFile mFile;

    public String getCommand(CommandsEnum command) {
        final FileConfiguration config = mFile.getFileConfiguration("config");
        String defaultCmd = command.toString().toLowerCase();

        return config.getString("commands." + defaultCmd + ".command", defaultCmd);
    }
}