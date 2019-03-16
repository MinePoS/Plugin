package net.minepos.plugin.common.utils;

import net.minepos.plugin.common.dependencies.Dependency;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DependencyUtils {
    public static Dependency shorthand(String shorthand, String... repoUrl) {
        String[] bits = shorthand.split(":");

        if (bits.length >= 3) {
            Dependency.DependencyBuilder builder = Dependency.builder()
                    .groupId(bits[0])
                    .artifactId(bits[1])
                    .version(bits[2]);

            if (repoUrl.length >= 1) {
                builder.repoUrl(repoUrl[0]);
            }

            return builder.build();
        }

        return null;
    }
}
