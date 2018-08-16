package net.minepos.plugin.core.storage.yaml;

import com.google.inject.Inject;
import net.minepos.plugin.core.objects.enums.CommandsEnum;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Commands {
    @Inject private MFile mFile;
    @Inject private Lang lang;

    public String getCommand(CommandsEnum command) {
        String commandStr = command.toString().toLowerCase();

        return mFile.getFileConfiguration("config").getString("commands." + commandStr + ".command", commandStr);
    }

    public String getPermission(CommandsEnum command) {
        String commandStr = command.toString().toLowerCase();

        return mFile.getFileConfiguration("config").getString("commands." + commandStr + ".permission", "minepos." + commandStr);
    }

    public String getUsage(CommandsEnum command) {
        String commandStr = command.toString().toLowerCase();

        return lang.get("ingame.invalid-command-usage", mFile.getFileConfiguration("config").getString("commands." + commandStr + ".usage", commandStr + " <required args> [optional args]"));
    }
}