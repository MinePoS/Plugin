package net.minepos.plugin.core.objects;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class BuyNotification extends BukkitRunnable {
    private final UUID uuid;
    private final String[] commands;

    // fetch uuid on minepos from mojang
    public BuyNotification(UUID uuid, String... commands) {
        this.uuid = uuid;
        this.commands = commands;
    }

    @Override
    public void run() {
        Arrays.stream(commands).forEach(cmd -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd));
    }
}
