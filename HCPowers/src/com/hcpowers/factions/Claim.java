package com.hcpowers.factions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class Claim {

    @Getter
    @Setter
    private Faction faction;

    @Getter
    @Setter
    private Location loc1;

    @Getter
    @Setter
    private Location loc2;


    public Claim (Faction faction, Location loc1, Location loc2) {

        this.faction = faction;

        this.loc1 = loc1;
        this.loc2 = loc2;
    }
}
