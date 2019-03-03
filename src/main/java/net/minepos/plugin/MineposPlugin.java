package net.minepos.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.minepos.plugin.core.framework.BinderModule;
import net.minepos.plugin.core.framework.dependencies.DependencyLoader;
import net.minepos.plugin.core.framework.dependencies.MavenLibraries;
import net.minepos.plugin.core.framework.dependencies.MavenLibrary;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@MavenLibraries(
        value = {
                @MavenLibrary(groupId = "org.reflections", artifactId = "reflections", version = "0.9.11"),
                @MavenLibrary(groupId = "javassist", artifactId = "javassist", version = "3.12.1.GA"),
                @MavenLibrary(groupId = "com.google.inject", artifactId = "guice", version = "4.2.2"),
                @MavenLibrary(groupId = "javax.inject", artifactId = "javax.inject", version = "1"),
                @MavenLibrary(groupId = "aopalliance", artifactId = "aopalliance", version = "1.0"),
                @MavenLibrary(groupId = "org.apache.commons", artifactId = "commons-lang3", version = "3.8.1"),
                @MavenLibrary(groupId = "commons-io", artifactId = "commons-io", version = "2.6"),
                @MavenLibrary(groupId = "org.nanohttpd", artifactId = "nanohttpd", version = "2.3.1"),
                @MavenLibrary(groupId = "org.nanohttpd", artifactId = "nanohttpd-websocket", version = "2.3.1")
        }
)
public final class MineposPlugin extends JavaPlugin {
    @Inject private WebSocketManager webSocketManager;

    @Override
    public void onEnable() {
        DependencyLoader.loadAll(getClass());

        Injector injector = new BinderModule(this).createInjector();
        injector.injectMembers(this);

        webSocketManager.start();
    }

    @Override
    public void onDisable() {
        webSocketManager.stop();
    }
}
