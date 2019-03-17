package net.minepos.plugin.common.dependencies;

import com.google.common.io.ByteStreams;
import net.minepos.plugin.common.logging.PluginLogger;
import net.minepos.plugin.common.plugin.MineposPlugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Arrays;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class DependencyLoader {
    private static final Method ADD_URL_METHOD;
    private static final MessageDigest DIGEST;

    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);

            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final MineposPlugin plugin;

    public DependencyLoader(MineposPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadClass(Class<?> clazz) {
        MavenLibrary[] libs = clazz.getDeclaredAnnotationsByType(MavenLibrary.class);

        if (libs != null) {
            for (MavenLibrary lib : libs) {
                load(Dependency.builder().groupId(lib.groupId()).artifactId(lib.artifactId()).version(lib.version()).repoUrl(lib.repo()).hash(lib.hash()).build());
            }
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "UnstableApiUsage"})
    public void load(Dependency d) {
        String name = d.getArtifactId() + "-" + d.getVersion();
        File lib = new File(plugin.getDataFolder() + "/libs/", name + ".jar");
        PluginLogger logger = plugin.getLogger();

        logger.info(String.format("Loading dependency %s:%s:%s from %s", d.getGroupId(), d.getArtifactId(), d.getVersion(), d.getRepoUrl()));

        if (!lib.exists()) {
            lib.getParentFile().mkdirs();

            try {
                logger.info("Dependency " + name + " is not currently downloaded, downloading now.");
                URL url = d.getUrl();

                try (InputStream is = url.openStream()) {
                    byte[] bytes = ByteStreams.toByteArray(is);

                    if (bytes.length == 0) {
                        throw new RuntimeException("Empty stream");
                    }

                    byte[] hash = DIGEST.digest(bytes);

                    if (!Arrays.equals(hash, d.getHash())) {

                    }

                    Files.copy(is, lib.toPath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!lib.exists()) {
            throw new RuntimeException("Unable to download dependency: " + d.toString());
        }

        URLClassLoader loader = (URLClassLoader) plugin.getClass().getClassLoader();
        try {
            ADD_URL_METHOD.invoke(loader, lib.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException("Unable to load dependency: " + d.toString());
        }

        logger.info("Successfully loaded dependency " + name);
    }
}
