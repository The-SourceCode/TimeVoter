package dev.thesourcecode.vote;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeVoteCommand implements CommandExecutor {
    private TimeVoter plugin;
    public TimeVoteCommand(TimeVoter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            World world = player.getWorld();

            if(!plugin.voteUtil.isOverworld(player)) return true;

            if (args.length == 1) {
                if(plugin.isVoteActive){
                    if(plugin.getYesVote().contains(player.getUniqueId()) || plugin.getNoVote().contains(player.getUniqueId())){
                        player.sendMessage(ChatColor.RED + "[TV] " + ChatColor.GRAY + "You have already voted");
                        return true;
                    }

                    if(args[0].equalsIgnoreCase("yes")){
                        player.sendMessage(ChatColor.GREEN + "[TV] " + ChatColor.GRAY + "You have voted Yes");
                        plugin.getServer().broadcastMessage(ChatColor.GREEN + "[TV] " + player.getName() + ChatColor.GRAY + " voted " + ChatColor.GREEN + "Yes");
                        plugin.getYesVote().add(player.getUniqueId());
                    }else if(args[0].equalsIgnoreCase("yes")){
                        player.sendMessage(ChatColor.RED + "[TV] " + ChatColor.GRAY + "You have voted No");
                        plugin.getServer().broadcastMessage(ChatColor.RED + "[TV] " + player.getName() + ChatColor.GRAY + " voted " + ChatColor.RED + "No");
                        plugin.getNoVote().add(player.getUniqueId());
                    }else{
                        player.sendMessage(ChatColor.RED + "[TV] " + ChatColor.GRAY + "Invalid use: /timevote <yes/no>");
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "[TV] " + ChatColor.GRAY + "There is no active vote.");
                }
            }else if(args.length == 0){
                if(!plugin.isVoteActive){
                    plugin.voteUtil.startVote(player);
                }
            }
        }
        return true;
    }
}
