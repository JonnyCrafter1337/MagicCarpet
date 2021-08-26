import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicCarpetMain extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("Magic is on it´s way");
        PluginManager pm = getServer().getPluginManager();
        GameListener listener = new GameListener(this);
        pm.registerEvents(listener, this);
        this.getCommand("mc").setExecutor(new CommandMc());
        getCommand("mc").setTabCompleter(new McTabCompleter());
        ItemManager.init();
    }

    @Override
    public void onDisable() {
        getLogger().info("Magic is leaving :c");
    }
}
