package net.minepos.plugin.http.commands;

import net.minepos.plugin.core.framework.HTTPAction;
import net.minepos.plugin.core.objects.enums.HTTPActions;

import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class RunCommands extends HTTPAction {
    public RunCommands() {
        super(HTTPActions.RUNCOMMAND);
    }

    @Override
    protected void execute(Map<String, List<String>> args) {
        System.out.println(args);
    }
}
