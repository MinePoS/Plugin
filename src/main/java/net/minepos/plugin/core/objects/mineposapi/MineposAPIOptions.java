package net.minepos.plugin.core.objects.mineposapi;

import lombok.Builder;
import lombok.Data;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder @Data
public final class MineposAPIOptions {
    @Builder.Default private boolean cache = true;
    private String apiKey;
}
