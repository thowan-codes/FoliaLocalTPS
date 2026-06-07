package legendary.hardcore.canvaslocaltps;

import legendary.hardcore.localtps.TpsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;

final class CanvasTpsProvider implements TpsProvider {
    @Override
    public double[] localTps(Location location) {
        return Bukkit.getRegionTPS(location);
    }

    @Override
    public double[] globalTps() {
        return Bukkit.getTPS();
    }
}
