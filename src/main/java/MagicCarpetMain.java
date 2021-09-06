import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicCarpetMain extends JavaPlugin {
    private static MagicCarpetMain instance;
    GameListener listener;
    @Override
    public void onEnable() {

        getLogger().info("Magic is on it´s way");
        PluginManager pm = getServer().getPluginManager();
        listener = new GameListener(this);
        pm.registerEvents(listener, this);
        this.getCommand("mc").setExecutor(new CommandMc(this));
        getCommand("mc").setTabCompleter(new McTabCompleter());

    }

    public GameListener getListener() {
        return listener;
    }

    @Override
    public void onDisable() {
        getLogger().info("Magic is leaving :c");
    }


}
