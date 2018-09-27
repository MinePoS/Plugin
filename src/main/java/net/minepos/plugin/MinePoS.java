package net.minepos.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.minepos.plugin.commands.Help;
import net.minepos.plugin.commands.Test;
import net.minepos.plugin.core.framework.BinderModule;
import net.minepos.plugin.core.handlers.BaseCommandHandler;
import net.minepos.plugin.core.handlers.CommandHandler;
import net.minepos.plugin.core.objects.enums.Registerables;
import net.minepos.plugin.core.objects.gui.ConfigGUIParser;
import net.minepos.plugin.core.storage.yaml.MFile;
import net.minepos.plugin.http.WebServer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

import static net.minepos.plugin.core.objects.enums.Registerables.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class MinePoS extends JavaPlugin {
    @Inject private MFile mFile;
    @Inject private CommandHandler commandHandler;
    @Inject private BaseCommandHandler baseCommandHandler;
    @Inject private WebServer webServer;

    @Inject private Test test;
    @Inject private Help help;

    @Override
    public void onEnable() {
        Stream.of(
                GUICE, FILES, COMMANDS, WEBSERVER, GUI
        ).forEach(this::register);
    }

    private void register(Registerables registerable) {
        switch (registerable) {
            case GUICE:
                BinderModule module = new BinderModule(this);
                Injector injector = module.createInjector();
                injector.injectMembers(this);

                break;

            case FILES:
                mFile.make("config", getDataFolder() + "/config.yml", "/config.yml");
                mFile.make("lang", getDataFolder() + "/lang.yml", "/lang.yml");

                final FileConfiguration config = mFile.getFileConfiguration("config");

                mFile.make("gui/main", getDataFolder() + "/" + config.getString("guis.main"), "/gui/main.yml");

                config.getStringList("guis.other").forEach(i -> mFile.make(
                        (i.startsWith("gui/") ? "" : "gui/") + i.toLowerCase().replace(".yml", ""),
                        getDataFolder() + i,
                        ""
                ));

                break;

            case COMMANDS:
                baseCommandHandler.init();

                Stream.of(
                        test, help
                ).forEach(commandHandler.getCommandsList()::add);

                break;

            case WEBSERVER:
                if (mFile.getFileConfiguration("config").getBoolean("http.enabled", true)) {
                    webServer.startServer();
                }

                break;

            case GUI:
                mFile.getItemMaps().keySet().forEach(key -> {
                    if (key.startsWith("gui/")) {
                        ConfigGUIParser.parseGUI(mFile.getFileConfiguration(key), key.replaceFirst("gui/", ""));
                    }
                });

                break;
        }
    }
}