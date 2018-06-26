package net.minepos.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.minepos.plugin.commands.Test;
import net.minepos.plugin.core.framework.BinderModule;
import net.minepos.plugin.core.handlers.CommandHandler;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class MinePoS extends JavaPlugin {
    @Inject private MFile mFile;
    @Inject private CommandHandler commandHandler;

    @Inject private Test test;

    @Override
    public void onEnable() {
        BinderModule module = new BinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        mFile.make("config", getDataFolder() + "/config.yml", "/config.yml");

        // mFile.getFileConfiguration("config");

        Stream.of(
                test
        ).forEach(commandHandler.getCommands()::add);

        getCommand("minepos").setExecutor(commandHandler);
    }
}