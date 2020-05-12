package dev.thesourcecode.vote;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class VoteEvent implements Listener {

    private TimeVoter plugin;
    public VoteEvent(TimeVoter plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void sleepVote(PlayerBedEnterEvent event){
        Player player = event.getPlayer();
        World world =player.getWorld();

        if(world.hasStorm()){
            world.setStorm(false);
            player.getServer().broadcastMessage(ChatColor.GREEN + "[TV] " + player.getName()
                    + ChatColor.GRAY + " has slept and reset the weather to clear.");
            return;
        }

        if(!plugin.isVoteActive){
            if(!plugin.voteUtil.isOverworld(player)) return;

            plugin.voteUtil.startVote(player);
        }else{
            if(!plugin.getYesVote().contains(player.getUniqueId())){
                plugin.getYesVote().add(player.getUniqueId());
                player.sendMessage(ChatColor.YELLOW + "[TV] " + ChatColor.GRAY + "Because you chose to sleep you are starting a time vote.");
            }
        }
    }
}
