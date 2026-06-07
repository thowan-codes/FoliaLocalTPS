package legendary.hardcore.localtps;

import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TpsCommand implements TabExecutor {
    private static final String[] REGION_LABELS = {"5s", "15s", "1m", "5m", "15m"};
    private static final String[] GLOBAL_LABELS = {"1m", "5m", "15m"};

    private final LocalTpsPlugin plugin;
    private final TpsProvider tpsProvider;

    TpsCommand(LocalTpsPlugin plugin, TpsProvider tpsProvider) {
        this.plugin = plugin;
        this.tpsProvider = tpsProvider;
    }

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String[] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.settings().badge()
                .append(Component.text(" /" + label + " is player-only.", NamedTextColor.RED)));
            return true;
        }

        TpsView view = TpsView.fromCommand(command.getName());
        player.getScheduler().run(plugin, task -> sendTps(player, view), null);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String alias,
        @NotNull String[] args
    ) {
        return Collections.emptyList();
    }

    private void sendTps(Player player, TpsView view) {
        PluginSettings settings = plugin.settings();
        Location location = player.getLocation();
        double[] regionTps = view.includesLocal() ? tpsProvider.localTps(location) : null;
        double[] globalTps = view.includesGlobal() ? tpsProvider.globalTps() : null;

        player.sendMessage(settings.badge().append(Component.text(" TPS snapshot", settings.timeColor())));
        if (view.includesLocal()) {
            player.sendMessage(tpsLine(settings, "Region", regionTps, REGION_LABELS));
        }
        if (view.includesGlobal()) {
            player.sendMessage(tpsLine(settings, "Global", globalTps, GLOBAL_LABELS));
        }

        if (plugin.isDebugEnabled()) {
            plugin.getLogger().info(
                "TPS debug for " + player.getName()
                    + " at " + location.getWorld().getName()
                    + " " + formatTpsValue(location.getX())
                    + ", " + formatTpsValue(location.getY())
                    + ", " + formatTpsValue(location.getZ())
                    + ": region=" + (regionTps == null ? "unavailable" : plainTps(regionTps, REGION_LABELS))
                    + " global=" + (globalTps == null ? "not requested" : plainTps(globalTps, GLOBAL_LABELS))
            );
        }
    }

    private Component tpsLine(PluginSettings settings, String area, double[] tps, String[] labels) {
        Component line = Component.text(area + ": ", settings.areaColor());
        if (tps == null) {
            return line.append(Component.text("unavailable", settings.unavailableColor()));
        }

        int entries = Math.min(tps.length, labels.length);
        for (int i = 0; i < entries; i++) {
            if (i > 0) {
                line = line.append(Component.text(", ", settings.timeColor()));
            }

            double value = cappedTps(tps[i]);
            line = line
                .append(Component.text(labels[i] + ": ", settings.timeColor()))
                .append(Component.text(formatTpsValue(value), settings.colorForTps(value)));
        }

        return line;
    }

    private String plainTps(double[] tps, String[] labels) {
        StringBuilder builder = new StringBuilder();

        int entries = Math.min(tps.length, labels.length);
        for (int i = 0; i < entries; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(labels[i]).append(": ").append(formatTpsValue(cappedTps(tps[i])));
        }

        return builder.toString();
    }

    private double cappedTps(double tps) {
        return Math.min(20.0D, tps);
    }

    private String formatTpsValue(double tps) {
        long scaled = Math.round(tps * 100.0D);
        long whole = scaled / 100L;
        long decimal = Math.abs(scaled % 100L);
        return whole + "." + (decimal < 10L ? "0" : "") + decimal;
    }

    private enum TpsView {
        ALL,
        LOCAL,
        GLOBAL;

        static TpsView fromCommand(String commandName) {
            return switch (commandName.toLowerCase()) {
                case "tps-local" -> LOCAL;
                case "tps-global" -> GLOBAL;
                default -> ALL;
            };
        }

        boolean includesLocal() {
            return this == ALL || this == LOCAL;
        }

        boolean includesGlobal() {
            return this == ALL || this == GLOBAL;
        }
    }
}
