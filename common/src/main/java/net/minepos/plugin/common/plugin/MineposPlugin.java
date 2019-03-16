package net.minepos.plugin.common.plugin;

import net.minepos.plugin.common.dependencies.Dependency;
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
    protected final List<Dependency> dependencies = Stream.of(
            DependencyUtils.shorthand("org.reflections:reflections:0.9.11"),
            DependencyUtils.shorthand("org.javassist:javassist:3.24.1-GA"),
            DependencyUtils.shorthand("com.google.inject:guice:4.2.2"),
            DependencyUtils.shorthand("javax.inject:javax.inject:1"),
            DependencyUtils.shorthand("aopalliance:aopalliance:1.0"),
            DependencyUtils.shorthand("org.apache.commons:commons-lang3:3.8.1"),
            DependencyUtils.shorthand("commons-io:commons-io:2.6"),
            DependencyUtils.shorthand("org.apache.httpcomponents:httpclient:4.5.7"),
            DependencyUtils.shorthand("org.apache.httpcomponents:httpcore:4.4.11"),
            DependencyUtils.shorthand("commons-logging:commons-logging:1.2"),
            DependencyUtils.shorthand("me.piggypiglet:TimeAPI:1.3", "https://repo.piggypiglet.me/repository/maven-releases/")
    ).collect(Collectors.toList());

    public void load() {

    }

    protected abstract PluginLogger getLogger();

    protected abstract String getDataFolder();
}
