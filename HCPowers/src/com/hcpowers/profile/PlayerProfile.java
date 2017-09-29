package com.hcpowers.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class PlayerProfile {

    @Getter
    @Setter
    private int lives;

    @Getter
    @Setter
    private long deathban;

    @Getter
    @Setter
    private boolean toggleChat;

    @Getter
    @Setter
    private boolean togglepm;

    @Getter
    @Setter
    private int pvptimer;

    @Getter
    @Setter
    private int pvpprot;

    @Getter
    @Setter
    private Player player;

    public PlayerProfile(Player player) {
        this.player = player;
    }

}
