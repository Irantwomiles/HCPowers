package com.hcpowers.factions.factionevents;

import com.hcpowers.factions.Faction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveClaim extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private Player player;

    @Getter
    @Setter
    private Faction faction;

    public PlayerLeaveClaim(Player player, Faction faction) {

        this.setPlayer(player);
        this.setFaction(faction);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
