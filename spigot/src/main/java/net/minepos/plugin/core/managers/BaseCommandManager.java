package net.minepos.plugin.core.managers;

import com.google.inject.Inject;
import net.minepos.plugin.MineposJavaPlugin;
import net.minepos.plugin.core.storage.file.GFile;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BaseCommandManager {
    @Inject private GFile gFile;
    @Inject private MineposJavaPlugin main;
    @Inject private CommandManager commandManager;

    private CommandMap commandMap;

    public void register() {
        Server server = Bukkit.getServer();

        try {
            if (server.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(server.getPluginManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerCommands();
    }

    private void registerCommands() {
        final FileConfiguration config = gFile.getFileConfiguration("commands");

        List<String> baseCommandsList = config.getStringList("base-commands");
        String[] baseCommands = baseCommandsList.toArray(new String[0]);
        baseCommandsList.remove(0);

        registerCommand(baseCommands);
        main.getCommand(baseCommands[0]).setExecutor(commandManager);
        main.getCommand(baseCommands[0]).setDescription(config.getString("base-command-description", "MinePoS Base Command"));
        main.getCommand(baseCommands[0]).setAliases(baseCommandsList);
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
        PluginCommand command = getCommand(aliases[0], main);

        command.setAliases(Arrays.asList(aliases));
        commandMap.register(main.getDescription().getName(), command);
    }
}
