package net.minepos.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.framework.BinderModule;
import net.minepos.plugin.framework.Registerable;
import net.minepos.plugin.framework.dependencies.DependencyLoader;
import net.minepos.plugin.framework.dependencies.MavenLibraries;
import net.minepos.plugin.framework.dependencies.MavenLibrary;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minepos.plugin.core.enums.Registerables.GFILE;
import static net.minepos.plugin.core.enums.Registerables.MINEPOS_API;

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
                @MavenLibrary(groupId = "org.apache.httpcomponents", artifactId = "httpclient", version = "4.5.7"),
                @MavenLibrary(groupId = "org.apache.httpcomponents", artifactId = "httpcore", version = "4.4.11"),
                @MavenLibrary(groupId = "commons-logging", artifactId = "commons-logging", version = "1.2"),
                @MavenLibrary(groupId = "me.piggypiglet", artifactId = "TimeAPI", version = "1.3", repo = "https://repo.piggypiglet.me/repository/maven-releases/")
//                @MavenLibrary(groupId = "org.nanohttpd", artifactId = "nanohttpd", version = "2.3.1")
        }
)
public final class MineposPlugin extends JavaPlugin {
    @Inject @Named("Reflections") private Reflections reflections;

    @Override
    public void onEnable() {
        DependencyLoader.loadAll(getClass());

        Injector injector = new BinderModule(this).createInjector();
        injector.injectMembers(this);

        Map<Registerables, Registerable> registerables = reflections.getSubTypesOf(Registerable.class).stream().map(injector::getInstance).collect(Collectors.toMap(Registerable::getRegisterable, r -> r));
        Stream.of(GFILE, MINEPOS_API).map(registerables::get).forEach(Registerable::run);
    }
}
