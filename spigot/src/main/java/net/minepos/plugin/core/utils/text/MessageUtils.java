package net.minepos.plugin.core.utils.text;

import net.minepos.plugin.core.storage.file.GLang;
import org.bukkit.command.CommandSender;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class MessageUtils {
    public static void sendMessage(CommandSender sender, String key, Object... args) {
        sender.sendMessage(StringUtils.colour(GLang.getString("ingame.prefix") + " &r" + GLang.getString(key, args)));
    }
}
