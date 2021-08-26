import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class GameListener implements Listener {

    public GameListener(MagicCarpetMain plugin) {

    }

    @EventHandler
    private void onMovement(PlayerMoveEvent e){
        if (CommandMc.getIsCarpeting(e.getPlayer())){
            PlaceCarpet.placeBlocks(e.getPlayer(),e.getTo());
            PlaceCarpet.removeBlocks(e.getPlayer(),e.getTo());
        }
    }
    @EventHandler
    private void onBreakBlock(BlockBreakEvent e){
        if(PlaceCarpet.getLocationPlaced(e.getBlock().getLocation()) != null){
            e.setCancelled(true);
        }

    }
    @EventHandler
    private  void onGamemodeChange(PlayerGameModeChangeEvent e){
        if(CommandMc.getIsCarpeting(e.getPlayer()) && e.getNewGameMode() == GameMode.CREATIVE){
            CommandMc.delIsCarpeting(e.getPlayer());
            PlaceCarpet.stopCarpeting(e.getPlayer());
        }
    }
   /* @EventHandler
    private void onFalldmg(EntityDamageEvent.DamageCause){
    }*/
}
