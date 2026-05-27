package legendary.hardcore.folialocaltps;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class FoliaLocalTpsCommand implements TabExecutor {
    private static final String RELOAD_PERMISSION = "folialocaltps.reload";

    private final FoliaLocalTpsPlugin plugin;

    FoliaLocalTpsCommand(FoliaLocalTpsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String[] args
    ) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(RELOAD_PERMISSION)) {
                sender.sendMessage(plugin.settings().badge()
                    .append(Component.text(" You do not have permission to reload FoliaLocalTPS.", plugin.settings().badColor())));
                return true;
            }

            plugin.reloadConfig();
            plugin.reloadSettings();
            sender.sendMessage(plugin.settings().badge()
                .append(Component.text(" Configuration reloaded.", plugin.settings().goodColor())));
            return true;
        }

        sender.sendMessage(plugin.settings().badge()
            .append(Component.text(" Usage: /folialocaltps reload", plugin.settings().timeColor())));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String alias,
        @NotNull String[] args
    ) {
        if (args.length == 1 && sender.hasPermission(RELOAD_PERMISSION)) {
            String current = args[0].toLowerCase(Locale.ROOT);
            if ("reload".startsWith(current)) {
                return List.of("reload");
            }
        }

        return Collections.emptyList();
    }
}
