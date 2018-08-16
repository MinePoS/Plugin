package net.minepos.plugin.core.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minepos.plugin.MinePoS;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

// ------------------------------
// Copyright (c) PiggyPiglet & AndrewAubury 2018
// https://www.piggypiglet.me
// https://www.andrewa.pw
// ------------------------------
@Singleton
public final class BaseCommandHandler {
    @Inject private MinePoS minePoS;
    @Inject private CommandHandler commandHandler;
    @Inject private MFile mFile;

    private CommandMap commandMap;

    public void init() {
        try {
            if (getServer().getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(getServer().getPluginManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerCommands();
    }

    private void registerCommands() {
        final FileConfiguration config = mFile.getFileConfiguration("config");

        List<String> baseCommandsList = config.getStringList("commands.base-commands");
        String[] baseCommands = baseCommandsList.toArray(new String[0]);
        baseCommandsList.remove(0);

        registerCommand(baseCommands);
        minePoS.getCommand(baseCommands[0]).setExecutor(commandHandler);
        minePoS.getCommand(baseCommands[0]).setDescription(config.getString("commands.base-command-description", "MinePoS Base Command"));
        minePoS.getCommand(baseCommands[0]).setAliases(baseCommandsList);
    }

    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

    private void registerCommand(String... aliases) {
        PluginCommand command = getCommand(aliases[0], minePoS);

        command.setAliases(Arrays.asList(aliases));
        commandMap.register(minePoS.getDescription().getName(), command);
    }
}
