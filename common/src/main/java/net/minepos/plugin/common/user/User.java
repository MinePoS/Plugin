package net.minepos.plugin.common.user;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public interface User {
    boolean isPlayer();

    boolean hasPermission(String permission);

    void sendMessage(String msg);

    <T> T getServerImplementation();
}
