package net.minepos.plugin.implementations.commands;

import net.minepos.plugin.core.enums.Commands;
import net.minepos.plugin.core.objects.gui.GUI;
import net.minepos.plugin.core.objects.gui.GUIItem;
import net.minepos.plugin.framework.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    public TestCommand() {
        super(Commands.TEST);
        playerOnly = true;
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        GUIItem item = GUIItem.builder().displayName("oof").build();

        System.out.println(item);

        ((Player) sender).openInventory(GUI.builder().items(item).name("oof").slots(9).build().getInventory());

        return true;
    }
}
