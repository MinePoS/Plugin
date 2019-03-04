package net.minepos.plugin.implementations.registerables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.minepos.plugin.MineposAPI;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.core.objects.mineposapi.MineposAPIOptions;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.framework.Registerable;
import org.bukkit.configuration.file.FileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MineposAPIRegisterable extends Registerable {
    @Inject @Named("MinePoS") private MineposAPI api;
    @Inject private GFile gFile;

    public MineposAPIRegisterable() {
        super(Registerables.MINEPOS_API);
    }

    @Override
    protected void execute() {
        FileConfiguration config = gFile.getFileConfiguration("config");

        try {
            api.connect(MineposAPIOptions.builder().apiKey(config.getString("api.key")).cache(config.getBoolean("cache.enabled", true)).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
