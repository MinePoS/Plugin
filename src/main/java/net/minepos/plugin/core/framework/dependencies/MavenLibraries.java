package net.minepos.plugin.core.framework.dependencies;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
/// Credit to lucko, this dependency management system wouldn't be possible without his helper project to guide me
// https://lucko.me luck@lucko.me
// ------------------------------
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibraries {
    @Nonnull
    MavenLibrary[] value() default {};
}
