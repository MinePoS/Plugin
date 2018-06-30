package net.minepos.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.minepos.plugin.commands.Test;
import net.minepos.plugin.core.framework.BinderModule;
import net.minepos.plugin.core.handlers.CommandHandler;
import net.minepos.plugin.core.storage.yaml.MFile;
import net.minepos.plugin.http.WebServer;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class MinePoS extends JavaPlugin {
    @Inject private MFile mFile;
    @Inject private CommandHandler commandHandler;
    @Inject private WebServer webServer;

    @Inject private Test test;

    private CommandMap commandMap;

    @Override
    public void onEnable() {
        BinderModule module = new BinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        try {
            if (getServer().getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(getServer().getPluginManager());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        mFile.make("config", getDataFolder() + "/config.yml", "/config.yml");
        mFile.make("http", getDataFolder() + "/http.yml", "/http.yml");

        Stream.of(
                test
        ).forEach(commandHandler.getCommands()::add);

        List<String> baseCommandsList = mFile.getFileConfiguration("config").getStringList("base-commands");
        String[] baseCommands = baseCommandsList.toArray(new String[0]);
        baseCommandsList.remove(0);
        registerCommand(baseCommands);
        getCommand(baseCommands[0]).setExecutor(commandHandler);
        getCommand(baseCommands[0]).setDescription("MinePoS Base Command");
        getCommand(baseCommands[0]).setAliases(baseCommandsList);

        webServer.startServer();
        //getCommand("minepos").setExecutor(commandHandler);
    }

    public void registerCommand(String... aliases) {
        PluginCommand command = getCommand(aliases[0], this);

        command.setAliases(Arrays.asList(aliases));
        commandMap.register(this.getDescription().getName(), command);
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

}