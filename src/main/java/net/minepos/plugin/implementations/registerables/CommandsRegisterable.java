package net.minepos.plugin.implementations.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.minepos.plugin.MineposPlugin;
import net.minepos.plugin.core.enums.Registerables;
import net.minepos.plugin.core.managers.CommandManager;
import net.minepos.plugin.framework.Command;
import net.minepos.plugin.framework.Registerable;
import org.reflections.Reflections;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class CommandsRegisterable extends Registerable {
    @Inject @Named("Reflections") private Reflections reflections;
    @Inject private Injector injector;
    @Inject private MineposPlugin main;
    @Inject private CommandManager commandManager;

    public CommandsRegisterable() {
        super(Registerables.COMMANDS);
    }

    @Override
    protected void execute() {
        main.getCommand("test").setExecutor(commandManager);
        reflections.getSubTypesOf(Command.class).stream().map(injector::getInstance).forEach(commandManager.getCommands()::add);
    }
}
