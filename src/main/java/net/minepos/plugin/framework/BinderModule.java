package net.minepos.plugin.framework;

import com.google.inject.*;
import com.google.inject.name.Named;
import net.minepos.plugin.MineposAPI;
import net.minepos.plugin.MineposPlugin;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final MineposPlugin main;
    private final MineposAPI api = new MineposAPI();

    public BinderModule(MineposPlugin main) {
        this.main = main;
    }

    public Injector createInjector () {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        bind(MineposPlugin.class).toInstance(main);
        requestInjection(api);
    }

    @Provides
    @Singleton
    @Named("Reflections")
    public Reflections providesReflections() {
        return new Reflections("net.minepos.plugin");
    }

    @Provides
    @Singleton
    @Named("MinePoS")
    public MineposAPI providesMineposAPI() {
        return api;
    }
}
