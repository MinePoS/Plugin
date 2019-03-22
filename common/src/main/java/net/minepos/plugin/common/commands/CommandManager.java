package net.minepos.plugin.common.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.common.commands.framework.Command;
import net.minepos.plugin.common.commands.framework.Commands;
import net.minepos.plugin.common.file.GFile;
import net.minepos.plugin.common.file.framework.FileConfiguration;
import net.minepos.plugin.common.user.User;
import net.minepos.plugin.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class CommandManager {
    @Getter private final List<Command> commands = new ArrayList<>();

    private final FileConfiguration commandsCfg;

    @Inject
    public CommandManager(GFile gFile) {
        this.commandsCfg = gFile.getFileConfiguration("commands");
    }

    public boolean run(User user, String message) {
        for (Command command : commands) {
            Commands cmd = command.getCommand();

            if (StringUtils.startsWith(commandsCfg.getStringList("commands")))
        }
    }
}
