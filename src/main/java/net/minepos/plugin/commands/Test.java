package net.minepos.plugin.commands;

import com.google.inject.Inject;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import net.minepos.plugin.core.objects.gui.GUIBuilder;
import net.minepos.plugin.core.objects.gui.GUIItemBuilder;
import net.minepos.plugin.core.objects.gui.GUIItemMaterial;
import net.minepos.plugin.core.storage.yaml.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Command {
    @Inject private Lang lang;

    public Test() {
        super(CommandsEnum.TEST);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            List<String> desc = new ArrayList<>();
            Stream.of("&bmega oof", "&coof").forEach(desc::add);

            GUIItemBuilder guiItemBuilder = new GUIItemBuilder()
                    .setGuiItemMaterial(new GUIItemMaterial(Material.ANVIL, 1))
                    .setSlot(4)
                    .setDescription(desc)
                    .setDisplayName("ooooofff")
                    .build();

            if (guiItemBuilder != null) {
                new GUIBuilder((Player) sender)
                        .setSize(9)
                        .setTitle("gui")
                        .addItems(guiItemBuilder.getGuiItem())
                        .build();
            }
        } else {
            sender.sendMessage(lang.get("ingame.player-only"));
        }

        return true;
    }
}