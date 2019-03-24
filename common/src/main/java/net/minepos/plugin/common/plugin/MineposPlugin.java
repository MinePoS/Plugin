package net.minepos.plugin.common.plugin;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.minepos.plugin.common.dependencies.Dependency;
import net.minepos.plugin.common.dependencies.DependencyLoader;
import net.minepos.plugin.common.guice.BinderModule;
import net.minepos.plugin.common.logging.PluginLogger;
import net.minepos.plugin.common.registerables.Registerable;
import net.minepos.plugin.common.registerables.Registerables;
import net.minepos.plugin.common.utils.DependencyUtils;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minepos.plugin.common.registerables.Registerables.FILES;
// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MineposPlugin {
    @Inject @Named("I-Reflections") private Reflections iReflections;
    private Injector injector;

    @SuppressWarnings("SpellCheckingInspection")
    protected final List<Dependency> dependencies = Stream.of(
            DependencyUtils.shorthand("org.reflections:reflections:0.9.11", "Q0NBODg0MjhGOEE4OTE5REY4ODUxMDU4MzNENDVGRjA3QkQyNkY5ODVGOTZFRTU1NjkwNTUxMjE2QjU4QjRBMQ=="),
            DependencyUtils.shorthand("org.javassist:javassist:3.24.1-GA", "NUQ1N0VBNUIwRUM4Q0I0NjE0M0RGRTUyMUY4ODhCMjA4MDI4QkUxMjZGMjc0Q0M0Rjg1MkU2NDE3NTVGMTU1Mw=="),
            DependencyUtils.shorthand("com.google.inject:guice:4.2.2", "RDI1OEZGMUJEOUI4QjUyNzg3MkY4NDAyNjQ4MjI2NjU4QUQzMTQ5RjFGNDBFNzRCMDU2NkQ2OUU3RTA0MkZCQw=="),
            DependencyUtils.shorthand("javax.inject:javax.inject:1", "OTFDNzcwNDRBNTBDNDgxNjM2QzMyRDkxNkZEODlDOTExOEE3MjE5NTM5MDQ1MkM4MTA2NTA4MEY5NTdERTdGRg=="),
            DependencyUtils.shorthand("aopalliance:aopalliance:1.0", "MEFEREVDNjcwRkVEQ0QzRjExM0M1QzgwOTFENzgzMjgwRDIzRjc1RTNBQ0I4NDFCNjFBOUNEQjA3OTM3NkEwOA=="),
            DependencyUtils.shorthand("org.apache.commons:commons-lang3:3.8.1", "REFDODA3RjY1QjA3Njk4RkYzOUIxQjA3QkZFRjNEODdBRTNGRDQ2RDkxQkJGOEEyQkMwMkIyQTgzMTYxNkY2OA=="),
            DependencyUtils.shorthand("commons-io:commons-io:2.6", "Rjg3N0QzMDQ2NjBBQzJBMTQyRjM4NjVCQURGQzk3MURFQzdFRDczQzc0N0M3RjhENUQyRjUxMzlDQTczNjUxMw=="),
            DependencyUtils.shorthand("org.apache.httpcomponents:httpclient:4.5.7", "ODA3RTlDNzNGMjdBNEIxOUREMDRCMUI2NzEyNjUzMkZDNzRCMEEzN0JEOEQxM0ZCQUQwNzNBRDc0RDA3ODMzMA=="),
            DependencyUtils.shorthand("org.apache.httpcomponents:httpcore:4.4.11", "RDc5OTUyMkQ1NzlBQUMwNkIxNzA2MDNGOEYwODBGNkUzMjQ4REFEQzAxRjk2NTJDREQ3RUE3QkMzMThDMjFDRQ=="),
            DependencyUtils.shorthand("commons-logging:commons-logging:1.2", "REFEREVBMUVBMEJFMEY1Njk3OEFCMzAwNkI4QUM5MjgzNEFGRUVGQkQ5QjdFNEU2MzE2RkNBNTdERjBGQTYzNg=="),
            DependencyUtils.shorthand("me.piggypiglet:TimeAPI:1.3", "M0JGRTQ3QTI5OUYzNzlDMjEwMjUyMDg0QTkyMjZEN0JBMzRDOUQ1RDhCNDQ3OTdENDkyOUY1QzkxOTU4N0FBMA==", "https://repo.piggypiglet.me/repository/maven-releases/")
    ).collect(Collectors.toList());

    private final DependencyLoader dependencyLoader = new DependencyLoader(this);

    private void load() {
        dependencies.forEach(dependencyLoader::load);
        dependencyLoader.loadClass(getClass());

        injector = new BinderModule(this).createInjector();
        injector.injectMembers(this);
    }

    protected final void load(Class<? extends Registerable>... registerables) {
        load();
        enable(Arrays.stream(registerables).map(injector::getInstance).toArray(Registerable[]::new));
    }

    protected final void load(Registerable... registerables) {
        load();
        enable(registerables);
    }

    private void enable(Registerable... registerables) {
        Map<Registerables, Registerable> registerablesMap = getRegisterableMap(iReflections, injector);

        Stream.of(FILES).map(registerablesMap::get).forEach(Registerable::run);
        Arrays.stream(registerables).forEach(Registerable::run);
    }

    private Map<Registerables, Registerable> getRegisterableMap(Reflections reflections, Injector injector) {
        return reflections.getSubTypesOf(Registerable.class).stream().map(injector::getInstance).collect(Collectors.toMap(Registerable::getRegisterable, r -> r));
    }

    public abstract String getPackage();

    public abstract PluginLogger getPluginLogger();

    public abstract String getPluginFolder();
}
