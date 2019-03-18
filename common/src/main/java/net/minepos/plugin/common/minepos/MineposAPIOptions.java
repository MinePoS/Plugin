package net.minepos.plugin.common.minepos;

import lombok.Builder;
import lombok.Data;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder @Data
public final class MineposAPIOptions {
    private boolean cache;
    private String apiKey;
}