package net.minepos.plugin.common.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.minepos.plugin.common.plugin.MineposPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final MineposPlugin main;

    public BinderModule(MineposPlugin main) {
        this.main = main;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        bind(MineposPlugin.class).toInstance(main);
    }
}
