package net.minepos.plugin.core.storage.file;

import com.google.inject.Inject;
import net.minepos.plugin.core.enums.Commands;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class GCommands {
    @Inject private static GFile gFile;

    public static String[] getCommands(Commands command) {
        return gFile.getFileConfiguration("commands").getString(commandString(command) + ".command", command.toString().toLowerCase()).split("/");
    }

    public static String getDescription(Commands command) {
        return gFile.getFileConfiguration("commands").getString(commandString(command) + ".description", "MinePoS command.");
    }

    public static String[] getPermissions(Commands command) {
        return gFile.getFileConfiguration("commands").getString(commandString(command) + ".permission", "minepos." + commandString(command)).split("/");
    }

    public static String getUsage(Commands command) {
        return gFile.getFileConfiguration("commands").getString(commandString(command) + ".usage", "<required args> [optional args]");
    }

    public static String commandString(Commands command) {
        return command.toString().toLowerCase();
    }
}
