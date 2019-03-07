package net.minepos.plugin.implementations.registerables;

import com.google.inject.Inject;
import net.minepos.plugin.MineposAPI;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.core.objects.tasks.Task;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.framework.Registerable;
import org.bukkit.configuration.file.FileConfiguration;
import sh.okx.timeapi.TimeAPI;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TasksRegisterable extends Registerable {
    @Inject private MineposAPI api;
    @Inject private GFile gFile;

    public TasksRegisterable() {
        super(Registerables.TASKS);
    }

    @Override
    protected void execute() {
        FileConfiguration config = gFile.getFileConfiguration("config");

        if (config.getBoolean("cache.enabled")) {
            Task.asyncInterval(r -> api.populateMap(), new TimeAPI(config.getString("cache.refresh-time")), false);
        }
    }
}
