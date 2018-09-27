package net.minepos.plugin.core.handlers;

import com.google.inject.Inject;
import net.minepos.plugin.MinePoS;
import net.minepos.plugin.core.framework.Command;
import net.minepos.plugin.core.objects.CommandNotification;
import net.minepos.plugin.core.storage.yaml.MFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ------------------------------
 * Copyright (c) AndrewAubury 2018
 * https://www.andrewa.pw
 * Project: MinePoS-Plugin
 * ------------------------------
 */
public class CommandQueueHandler {

    @Inject
    private MFile mFile;

    private static CommandQueueHandler ourInstance = new CommandQueueHandler();

    public static CommandQueueHandler getInstance() {
        return ourInstance;
    }

    public static Map<CommandNotification, UUID> commandMap;
    private JavaPlugin plugin;

    private CommandQueueHandler() {
        commandMap = new HashMap<CommandNotification, UUID>();

        plugin= JavaPlugin.getProvidingPlugin(MinePoS.class);

       plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
           @Override
           public void run() {
               checkNotifications();
           }
       }, 20l * 60l, 20l * 60l);

    }

    public void checkNotifications(){
        for(CommandNotification cn : commandMap.keySet()){
            UUID uuid = commandMap.get(cn);
            boolean waitForPlayer = mFile.getFileConfiguration("config.yml").getBoolean("need-player-online");
            if(waitForPlayer){
                if(cn.getUuid() == null || Bukkit.getServer().getOfflinePlayer(uuid).isOnline()){
                    runNotification(cn);
                }
            }else{
                runNotification(cn);
            }
        }
    }

    public void runNotification(CommandNotification cn){
        cn.run();
        //TODO: Make this send the api call back to the MinePoS install to confirm the command has ran.
    }
}
