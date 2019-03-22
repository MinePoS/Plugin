package net.minepos.plugin.common.commands.framework;

import lombok.Getter;
import net.minepos.plugin.common.user.User;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Command {
    @Getter private final Commands command;
    @Getter protected boolean playerOnly = false;

    protected Command(Commands command) {
        this.command = command;
    }

    protected abstract boolean execute(User sender, String[] args);

    public boolean run(User sender, String[] args) {
        return execute(sender, args);
    }
}
