package net.minepos.plugin.core.framework;

import lombok.Getter;
import net.minepos.plugin.core.objects.enums.HTTPActions;

import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public abstract class HTTPAction {
    @Getter private final HTTPActions action;

    protected HTTPAction() {
        this(null);
    }

    protected HTTPAction(HTTPActions action) {
        this.action = action;
    }

    protected abstract void execute(Map<String, List<String>> args);

    public void run(Map<String, List<String>> args) {
        execute(args);
    }
}
