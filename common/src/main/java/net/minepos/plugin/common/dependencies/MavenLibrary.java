package net.minepos.plugin.common.dependencies;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Repeatable(MavenLibraries.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibrary {
    @Nonnull
    String groupId() default "";

    @Nonnull
    String artifactId() default "";

    @Nonnull
    String version() default "";

    @Nonnull
    String shorthand() default "";

    @Nonnull
    String repo() default "https://repo1.maven.org/maven2";

    @Nonnull
    String hash();
}