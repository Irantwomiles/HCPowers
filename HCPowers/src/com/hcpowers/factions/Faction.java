package com.hcpowers.factions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Faction {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String leader;

    @Getter
    @Setter
    private String motd;

    @Getter
    @Setter
    private List<String> members;

    @Getter
    @Setter
    private List<String> captains;

    @Getter
    @Setter
    private List<String> invites;

    @Getter
    @Setter
    private double dtr;

    @Getter
    @Setter
    private double maxDtr;

    @Getter
    @Setter
    private int balance;

    @Getter
    @Setter
    private int freezetime;

    @Getter
    @Setter
    private boolean system;

    @Getter
    @Setter
    private boolean deathban;

    @Getter
    @Setter
    private Location home;

    @Getter
    @Setter
    private Location loc1;

    @Getter
    @Setter
    private Location loc2;

    public Faction(String name) {

        this.name = name;
        this.motd = "Welcome to HCPowers.com!";

        system = false;
        deathban = true;

        dtr = 1.01;

        balance = 100000;

        members = new ArrayList<>();
        captains = new ArrayList<>();
        invites = new ArrayList<>();

    }

    public boolean isRaidable() {
        if(dtr < 0) {
            return true;
        }

        return false;
    }

}
