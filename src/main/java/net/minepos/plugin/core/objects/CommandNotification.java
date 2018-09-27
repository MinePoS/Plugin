package net.minepos.plugin.core.objects;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class CommandNotification extends BukkitRunnable {
    @Getter private final UUID uuid;
    @Getter private final String command;
    @Getter private final String queueid;
    //fetch uuid on minepos from mojang
    public CommandNotification(UUID uuid, String queueid,String command) {
        this.uuid = uuid;
        this.command = command;
        this.queueid = queueid;
    }

    @Override
    public void run() {
        MinePoSSender sender = new MinePoSSender(queueid);
        Bukkit.getServer().dispatchCommand(sender, command);
    }


}
