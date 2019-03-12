package net.minepos.plugin.implementations.commands;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.minepos.plugin.MineposAPI;
import net.minepos.plugin.core.enums.Commands;
import net.minepos.plugin.core.objects.gui.GUI;
import net.minepos.plugin.core.objects.gui.GUIItem;
import net.minepos.plugin.core.objects.mineposapi.objects.Category;
import net.minepos.plugin.framework.Command;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    @Inject @Named("MinePoS") private MineposAPI api;

    public TestCommand() {
        super(Commands.TEST);
        playerOnly = true;
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        final Collection<Category> categories = api.getCategories().values();
        int categoriesSize = categories.size();

        if (categoriesSize >= 1) {
            GUI.GUIBuilder builder = GUI.builder();

            if (categoriesSize <= 9) {
                builder.slots(9);
            } else if (categoriesSize <= 27) {
                builder.slots(27);
            } else if (categoriesSize <= 54) {
                builder.slots(54);
            } else {
                return true;
            }

            List<GUIItem> items = new ArrayList<>();
            int i = 0;

            for (Category category : categories) {
                items.add(
                        GUIItem.builder()
                                .slot(i)
                                .material(Material.getMaterial(category.getMaterial().toUpperCase()))
                                .amount(1)
                                .displayName(category.getName())
                                .description(category.getShortDesc())
                                .build()
                );

                ++i;
            }

            ((Player) sender).openInventory(builder.items(items).name("Categories").build().getInventory());
        }

        return true;
    }
}
