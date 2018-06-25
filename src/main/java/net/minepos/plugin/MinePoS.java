package net.minepos.plugin;

import com.google.inject.Injector;
import net.minepos.plugin.core.framework.BinderModule;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class MinePoS extends JavaPlugin {
    @Override
    public void onEnable() {
        BinderModule module = new BinderModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);
    }
}
