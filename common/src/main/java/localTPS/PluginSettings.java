package localTPS;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.file.FileConfiguration;

public record PluginSettings(
    Component badge,
    TextColor badgeColor,
    TextColor badgeTextColor,
    TextColor areaColor,
    TextColor timeColor,
    TextColor goodColor,
    TextColor warningColor,
    TextColor badColor,
    TextColor unavailableColor
) {
    static PluginSettings fromConfig(FileConfiguration config, String pluginName) {
        TextColor badgeColor = color(config, "colors.badge", NamedTextColor.DARK_AQUA);
        TextColor badgeTextColor = color(config, "colors.badge-text", NamedTextColor.WHITE);

        return new PluginSettings(
            Component.text("[", badgeColor)
                .append(Component.text(pluginName, badgeTextColor))
                .append(Component.text("]", badgeColor)),
            badgeColor,
            badgeTextColor,
            color(config, "colors.area", NamedTextColor.GOLD),
            color(config, "colors.time", NamedTextColor.GRAY),
            color(config, "colors.tps.good", NamedTextColor.GREEN),
            color(config, "colors.tps.warning", NamedTextColor.YELLOW),
            color(config, "colors.tps.bad", NamedTextColor.RED),
            color(config, "colors.unavailable", NamedTextColor.RED)
        );
    }

    TextColor colorForTps(double tps) {
        if (tps >= 15.0D) {
            return goodColor;
        }

        if (tps >= 10.0D) {
            return warningColor;
        }

        return badColor;
    }

    private static TextColor color(FileConfiguration config, String path, NamedTextColor fallback) {
        String value = config.getString(path);
        if (value == null || value.isBlank()) {
            return fallback;
        }

        TextColor hexColor = TextColor.fromHexString(value);
        if (hexColor != null) {
            return hexColor;
        }

        NamedTextColor namedColor = NamedTextColor.NAMES.value(value.toLowerCase());
        return namedColor == null ? fallback : namedColor;
    }
}
