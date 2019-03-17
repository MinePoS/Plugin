package net.minepos.plugin.common.registerables.implementations;

import com.google.inject.Inject;
import net.minepos.plugin.common.file.GFile;
import net.minepos.plugin.common.plugin.MineposPlugin;
import net.minepos.plugin.common.registerables.Registerable;
import net.minepos.plugin.common.registerables.Registerables;

import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class GFileRegisterable extends Registerable {
    @Inject private GFile gFile;
    @Inject private MineposPlugin main;

    public GFileRegisterable() {
        super(Registerables.FILES);
    }

    @Override
    protected void execute() {
        gFile.clearMap();
        Stream.of("config", "commands", "lang").forEach(i -> gFile.make(i, main.getDataFolder() + "/" + i + ".yml", "/" + i + ".yml"));
    }
}
