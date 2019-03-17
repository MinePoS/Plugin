package net.minepos.plugin.common.plugin;

import net.minepos.plugin.common.dependencies.Dependency;
import net.minepos.plugin.common.dependencies.DependencyLoader;
import net.minepos.plugin.common.logging.PluginLogger;
import net.minepos.plugin.common.utils.DependencyUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class MineposPlugin {
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

    public void load() {
        dependencies.forEach(dependencyLoader::load);
        dependencyLoader.loadClass(getClass());
    }

    public abstract PluginLogger getLogger();

    public abstract String getDataFolder();
}
