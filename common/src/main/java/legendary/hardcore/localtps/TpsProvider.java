package legendary.hardcore.localtps;

import org.bukkit.Location;

public interface TpsProvider {
    double[] localTps(Location location);

    double[] globalTps();
}
