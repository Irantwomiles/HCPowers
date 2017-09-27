package com.hcpowers.core.utils.scoreboard;

import com.hcpowers.profile.ProfileManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerBoard {

    private static HashMap<String,ArrayList<String>> entries = new HashMap<>();
    private ProfileManager pm = new ProfileManager();

    public void loadEntries(Player player) {

        entries.put(player.getName(), new ArrayList<String>());

        entries.get(player.getName()).add(ChatColor.RED.toString());
        entries.get(player.getName()).add(ChatColor.DARK_RED.toString());
        entries.get(player.getName()).add(ChatColor.BLUE.toString());
        entries.get(player.getName()).add(ChatColor.DARK_BLUE.toString());
        entries.get(player.getName()).add(ChatColor.GREEN.toString());
        entries.get(player.getName()).add(ChatColor.DARK_GREEN.toString());
        entries.get(player.getName()).add(ChatColor.YELLOW.toString());
        entries.get(player.getName()).add(ChatColor.GOLD.toString());
        entries.get(player.getName()).add(ChatColor.AQUA.toString());
        entries.get(player.getName()).add(ChatColor.DARK_AQUA.toString());
        entries.get(player.getName()).add(ChatColor.GRAY.toString());
        entries.get(player.getName()).add(ChatColor.DARK_GRAY.toString());
        entries.get(player.getName()).add(ChatColor.LIGHT_PURPLE.toString());
        entries.get(player.getName()).add(ChatColor.DARK_PURPLE.toString());

        System.out.println(entries.get(player.getName()).size());

    }

    public void setPlayerBoard(Player player) {

        if(player.getScoreboard() == Bukkit.getScoreboardManager()) {

            Scoreboard scoreboard = player.getScoreboard();

            Objective obj = scoreboard.registerNewObjective("playerboard", "dummy");

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            obj.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "HCPowers" + ChatColor.GRAY.toString() + ChatColor.BOLD + " | " + ChatColor.RED.toString() + ChatColor.BOLD + "HCF");

            player.setScoreboard(scoreboard);

        } else {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective obj = scoreboard.registerNewObjective("playerboard", "dummy");

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            obj.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "HCPowers" + ChatColor.GRAY.toString() + ChatColor.BOLD  + " | " + ChatColor.RED.toString() + ChatColor.BOLD + "HCF");

            player.setScoreboard(scoreboard);

        }

    }

    public void addLine(Player player, Scoreboard scoreboard, String msg, String teamName) {

        if(!scoreboard.getTeams().contains(scoreboard.getTeam(teamName))) {

            Team team = scoreboard.registerNewTeam(teamName);

            team.addEntry(entries.get(player.getName()).get(0));

            String left = "", right = "";

            if(msg.length() < 16) {
                team.setPrefix(msg);
            } else {

                left = msg.substring(0, 16);
                right = ChatColor.getLastColors(left) + msg.substring(16, msg.length());

                team.setPrefix(left);
                team.setSuffix(right);
            }

            Objective obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);

            obj.getScore(entries.get(player.getName()).get(0)).setScore(scoreboard.getEntries().size() + 1);

            entries.get(player.getName()).remove(0);

        } else {

            /*Team team = scoreboard.getTeam(teamName);

            String left = "", right = "";

            if(msg.length() < 15) {
                team.setPrefix(msg);
            } else {
                left = msg.substring(0, 16);
                right = ChatColor.getLastColors(left) + msg.substring(16, msg.length());

                team.setPrefix(left);
                team.setSuffix(right);
            }*/

            update(player, player.getScoreboard(), msg, teamName);

        }

    }

    public void update(Player player, Scoreboard scoreboard, String msg, String teamName) {

        if(!scoreboard.getTeams().contains(scoreboard.getTeam(teamName))) {

            //TODO: Check for active KOTH's
            //PvP Protection

            addLine(player, player.getScoreboard(), msg, teamName);

        } else {

            Team team = scoreboard.getTeam(teamName);

            String left = "", right = "";

            if(msg.length() < 15) {
                team.setPrefix(msg);
            } else {
                left = msg.substring(0, 16);
                right = ChatColor.getLastColors(left) + msg.substring(16, msg.length());

                team.setPrefix(left);
                team.setSuffix(right);
            }

        }

    }

    public void removeLine(Player player, Scoreboard board, String teamName) {

        if(board.getObjective(DisplaySlot.SIDEBAR) != null) {

            if(board.getTeam(teamName) != null) {
                board.resetScores(board.getTeam(teamName).getEntries().iterator().next());

                board.getTeam(teamName).unregister();
            }
        }
    }

}
