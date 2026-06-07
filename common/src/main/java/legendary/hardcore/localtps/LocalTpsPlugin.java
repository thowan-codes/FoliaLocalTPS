package legendary.hardcore.localtps;

import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LocalTpsPlugin extends JavaPlugin {
    private boolean debugEnabled;
    private PluginSettings settings;

    @Override
    public final void onEnable() {
        saveDefaultConfig();
        reloadSettings();

        TpsCommand tpsCommand = new TpsCommand(this, createTpsProvider());
        registerTpsCommand("tps-all", tpsCommand);
        registerTpsCommand("tps-local", tpsCommand);
        registerTpsCommand("tps-global", tpsCommand);

        LocalTpsAdminCommand adminCommand = new LocalTpsAdminCommand(this);
        PluginCommand pluginCommand = Objects.requireNonNull(
            getCommand(adminCommandName()),
            adminCommandName() + " command is missing from plugin.yml"
        );
        pluginCommand.setExecutor(adminCommand);
        pluginCommand.setTabCompleter(adminCommand);

        getLogger().info(platformName() + "LocalTPS enabled.");
    }

    protected abstract TpsProvider createTpsProvider();

    public abstract String platformName();

    public abstract String adminCommandName();

    public abstract String reloadPermission();

    private void registerTpsCommand(String name, TpsCommand executor) {
        PluginCommand command = Objects.requireNonNull(getCommand(name), name + " command is missing from plugin.yml");
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }

    public final void reloadSettings() {
        debugEnabled = getConfig().getBoolean("debug", false);
        settings = PluginSettings.fromConfig(getConfig(), platformName() + "LocalTPS");
    }

    public final boolean isDebugEnabled() {
        return debugEnabled;
    }

    public final PluginSettings settings() {
        return settings;
    }
}
