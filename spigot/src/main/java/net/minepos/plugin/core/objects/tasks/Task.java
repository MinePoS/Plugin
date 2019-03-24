package net.minepos.plugin.core.objects.tasks;

import com.google.inject.Inject;
import net.minepos.plugin.MineposJavaPlugin;
import org.bukkit.Bukkit;
import sh.okx.timeapi.TimeAPI;

import java.util.function.Consumer;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Task {
    @Inject private static MineposJavaPlugin main;

    public static void async(Consumer<Runnable> task) {
        Bukkit.getScheduler().runTaskAsynchronously(main, runnable(task));
    }

    public static void asyncInterval(Consumer<Runnable> task, TimeAPI time, boolean straightAway) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(main, runnable(task), straightAway ? 0 : time.getTicks(), time.getTicks());
    }

    private static Runnable runnable(Consumer<Runnable> task) {
        return new Runnable() {
            @Override
            public void run() {
                task.accept(this);
            }
        };
    }
}
