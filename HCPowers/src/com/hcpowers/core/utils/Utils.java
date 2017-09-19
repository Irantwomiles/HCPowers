package com.hcpowers.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Utils {

    public String toMMSS(long dura){
        int minute = (int)(dura / 60.0D);
        long second = dura - (minute * 60);
        String formatted = "";
        {
            if(minute < 10){
                formatted += "";
            }
            formatted += minute;
            formatted += ":";
            if(second < 10){
                formatted += "0";
            }
            formatted += second;
        }
        return formatted;
    }

    public String formatDouble(double db) {
        DecimalFormat f = new DecimalFormat("#0.00");

        return f.format(db);
    }

    public void sendMessage(String msg) {

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            //Add check when profiles are added for players who muted chat

            p.sendMessage(msg);
        }

    }

}
