package com.hcpowers.koth.events;

import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectOnCapzone implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        for(Koth koth : KothManager.getManager().getKoths()) {
            if(koth.getPlayers().contains(event.getPlayer().getName())) {

                if(koth.getPlayers().get(0).equalsIgnoreCase(player.getName())) {
                    koth.setCurrentTimer(koth.getTimer());
                }

                koth.getPlayers().remove(event.getPlayer().getName());
            }

        }

    }
}
