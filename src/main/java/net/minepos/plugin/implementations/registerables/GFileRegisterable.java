package net.minepos.plugin.implementations.registerables;

import com.google.inject.Inject;
import net.minepos.plugin.MineposPlugin;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.core.storage.file.GFile;
import net.minepos.plugin.framework.Registerable;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class GFileRegisterable extends Registerable {
    @Inject private GFile gFile;
    @Inject private MineposPlugin mineposPlugin;

    public GFileRegisterable() {
        super(Registerables.GFILE);
    }

    @Override
    protected void execute() {
        gFile.clearMap();
        gFile.make("config", mineposPlugin.getDataFolder() + "/config.yml", "/config.yml");
    }
}
