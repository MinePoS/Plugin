package net.minepos.plugin.core.framework;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import net.minepos.plugin.MinePoS;
import net.minepos.plugin.core.objects.gui.ConfigGUIParser;
import net.minepos.plugin.core.storage.yaml.MFile;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final MinePoS plugin;

    public BinderModule(MinePoS plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        this.bind(MinePoS.class).toInstance(plugin);
        requestStaticInjection(ConfigGUIParser.class);
    }
}
