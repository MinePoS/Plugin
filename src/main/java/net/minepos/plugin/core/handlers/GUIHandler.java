package net.minepos.plugin.core.handlers;

import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.core.objects.gui.GUIBuilder;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class GUIHandler {
    @Getter private Map<String, GUIBuilder.GUI> guis;

    public GUIHandler() {
        guis = new ConcurrentHashMap<>();
    }

    public void open(String key, Player player) {
        player.openInventory(guis.get(key).getInventory());
    }
}
