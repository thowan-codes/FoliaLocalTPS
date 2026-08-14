package localTPS;
import org.bukkit.Bukkit;
import org.bukkit.Location;

final class FoliaTpsProvider implements TpsProvider {
    @Override
    public double[] localTps(Location location) {
        return Bukkit.getRegionTPS(location);
    }

    @Override
    public double[] globalTps() {
        return Bukkit.getTPS();
    }
}
