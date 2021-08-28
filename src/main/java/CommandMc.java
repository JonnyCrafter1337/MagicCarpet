import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;

public class CommandMc implements CommandExecutor {
    private static HashMap<Player, Boolean> isCarpeting = new HashMap<Player, Boolean>();


    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {

        if (commandSender instanceof Player) {
            Player sender = (((Player) commandSender).getPlayer());
            if (sender.getGameMode() == GameMode.SPECTATOR) {
                sender.sendMessage("You can´t use the Magic Carpet while spectating");
                return (true);
            }
            if (sender.getGameMode() == GameMode.CREATIVE) {
                sender.sendMessage("You can already fly");
                return (true);
            }
            if (args.length == 0) {
                sender.sendMessage("Please select on or off");
                return true;
            }
            if(args[0].equals("off")){

                PlaceCarpet.stopCarpeting(sender);
                return true;
            }else if(args[0].equals("on")){
                addIsCarpeting(sender);
            }else if(args[0].equals("debug")){
                Bukkit.getLogger().info("isCarpeting" + String.valueOf(isCarpeting.entrySet()));
                sender.sendMessage("isCarpeting" + String.valueOf(isCarpeting.entrySet()));
                PlaceCarpet.debug(sender);
            }else{
                sender.sendMessage("Please select on or off");
                return true;
            }

        }else {
            return false;
        }
        return true;
    }

    public static void addIsCarpeting(Player p) {
        isCarpeting.put(p, true);
    }
    public static void delIsCarpeting(Player p) {
        isCarpeting.remove(p);
    }
    public static Boolean getIsCarpeting(Player p){
        return isCarpeting.get(p);
    }
    public static Integer isCarpetingSize(){
        return isCarpeting.size();
    }
}