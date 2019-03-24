package net.minepos.plugin.framework;

import com.google.inject.*;
import com.google.inject.name.Named;
import net.minepos.plugin.MineposAPI;
import net.minepos.plugin.MineposJavaPlugin;
import net.minepos.plugin.core.objects.tasks.Task;
import net.minepos.plugin.core.storage.file.GCommands;
import net.minepos.plugin.core.storage.file.GLang;
import net.minepos.plugin.core.utils.http.WebUtils;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class BinderModule extends AbstractModule {
    private final MineposJavaPlugin main;
    private final MineposAPI api = new MineposAPI();

    public BinderModule(MineposJavaPlugin main) {
        this.main = main;
    }

    public Injector createInjector () {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        bind(MineposJavaPlugin.class).toInstance(main);
        requestInjection(api);
        requestStaticInjection(WebUtils.class, GCommands.class, GLang.class, Task.class);
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
