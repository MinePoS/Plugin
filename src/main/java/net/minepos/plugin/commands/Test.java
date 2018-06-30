package net.minepos.plugin.commands;

import com.google.inject.Inject;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.enums.CommandsEnum;
import net.minepos.plugin.core.objects.gui.GUIBuilder;
import net.minepos.plugin.core.objects.gui.GUIItem;
import net.minepos.plugin.core.utils.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2018
// https://www.piggypiglet.me
// ------------------------------
public final class Test extends Command {
    public Test() {
        super(CommandsEnum.TEST);
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            List<String> desc = new ArrayList<>();
            Stream.of(
                    "test",
                    "test2",
                    "&btest3"
            ).forEach(str -> desc.add(StringUtils.cc("&r" + str)));

            ItemStack itemStack = new ItemStack(Material.ANVIL, 1);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("oof");
            itemMeta.setLore(desc);
            itemStack.setItemMeta(itemMeta);

            GUIItem item = new GUIItem(4, itemStack);

            new GUIBuilder((Player) sender, "&bTest", 27).addItems(item).build();
        }
    }
}
