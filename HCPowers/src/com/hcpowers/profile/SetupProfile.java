package com.hcpowers.profile;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SetupProfile implements Listener {

    private ProfileManager pm = new ProfileManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        pm.loadProfile(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        pm.saveProfile(event.getPlayer());
    }
}
