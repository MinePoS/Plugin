package net.minepos.plugin.common.dependencies;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.Base64;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder @Data
public class Dependency {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String repoUrl;
    private final byte[] hash;

    public class DependencyBuilder {
        public DependencyBuilder hash(String hash) {
            this.hash = Base64.getDecoder().decode(hash);
            return this;
        }

        public Dependency build() {
            // Builder.Default doesn't work, so this is the next best thing.
            return new Dependency(
                    groupId == null ? "" : groupId,
                    artifactId == null ? "" : artifactId,
                    version == null ? "" : version,
                    repoUrl == null ? "https://repo1.maven.org/maven2" : repoUrl,
                    hash == null ? new byte[]{} : hash
            );
        }
    }

    public URL getUrl() {
        String repo = repoUrl;

        if (!repo.endsWith("/")) {
            repo += "/";
        }

        repo += "%s/%s/%s/%s-%s.jar";

        try {
            return new URL(String.format(repo, groupId.replace(".", "/"), artifactId, version, artifactId, version));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
