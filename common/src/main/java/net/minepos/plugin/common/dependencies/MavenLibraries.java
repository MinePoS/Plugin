package net.minepos.plugin.common.dependencies;

import javax.annotation.Nonnull;
import java.lang.annotation.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MavenLibraries {
    @Nonnull
    MavenLibrary[] value() default {};
}