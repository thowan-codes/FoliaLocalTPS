package legendary.hardcore.folialocaltps;

import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class FoliaLocalTpsPlugin extends JavaPlugin {
    private boolean debugEnabled;
    private PluginSettings settings;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadSettings();

        TpsCommand tpsCommand = new TpsCommand(this);
        registerTpsCommand("tps-all", tpsCommand);
        registerTpsCommand("tps-local", tpsCommand);
        registerTpsCommand("tps-global", tpsCommand);

        FoliaLocalTpsCommand foliaLocalTpsCommand = new FoliaLocalTpsCommand(this);
        PluginCommand pluginCommand = Objects.requireNonNull(
            getCommand("folialocaltps"),
            "folialocaltps command is missing from plugin.yml"
        );
        pluginCommand.setExecutor(foliaLocalTpsCommand);
        pluginCommand.setTabCompleter(foliaLocalTpsCommand);

        getLogger().info("FoliaLocalTPS enabled.");
    }

    private void registerTpsCommand(String name, TpsCommand executor) {
        PluginCommand command = Objects.requireNonNull(getCommand(name), name + " command is missing from plugin.yml");
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }

    public void reloadSettings() {
        debugEnabled = getConfig().getBoolean("debug", false);
        settings = PluginSettings.fromConfig(getConfig());
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public PluginSettings settings() {
        return settings;
    }
}
