package net.minepos.plugin.core.handlers;

import com.google.inject.Singleton;
import lombok.Getter;
import net.minepos.plugin.core.framework.HTTPAction;
import net.minepos.plugin.core.objects.enums.HTTPActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class HTTPHandler {
    @Getter private final List<HTTPAction> actions = new ArrayList<>();

    public void run(String uri, Map<String, List<String>> args) {
        actions.forEach(action -> {
            try {
                if (action.getAction() == HTTPActions.valueOf(uri.toUpperCase().trim().replace("/", ""))) {
                    action.run(args);
                }
            } catch (Exception ignored) {}
        });
    }
}
