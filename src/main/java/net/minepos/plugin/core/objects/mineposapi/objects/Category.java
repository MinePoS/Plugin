package net.minepos.plugin.core.objects.mineposapi.objects;

import lombok.Builder;
import lombok.Data;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Builder @Data
public final class Category {
    private final int id;
    private final String name;
    private final String shortDesc;
    private final boolean visible;
    private final String material;
}
