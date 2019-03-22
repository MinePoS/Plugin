package net.minepos.plugin.common.guice;

import com.google.inject.*;
import com.google.inject.name.Named;
import net.minepos.plugin.common.file.implementations.JsonFileConfiguration;
import net.minepos.plugin.common.plugin.MineposPlugin;
import org.reflections.Reflections;

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
        requestInjection(JsonFileConfiguration.class);
    }

    @Provides
    @Singleton
    @Named("I-Reflections")
    public Reflections providesIReflections() {
        return new Reflections("net.minepos.plugin.common");
    }

    @Provides
    @Singleton
    @Named("Reflections")
    public Reflections providesReflections() {
        return new Reflections(main.getPackage());
    }
}
