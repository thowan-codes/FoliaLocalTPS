package legendary.hardcore.canvaslocaltps;

import legendary.hardcore.localtps.LocalTpsPlugin;
import legendary.hardcore.localtps.TpsProvider;

public final class CanvasLocalTpsPlugin extends LocalTpsPlugin {
    @Override
    protected TpsProvider createTpsProvider() {
        return new CanvasTpsProvider();
    }

    @Override
    public String platformName() {
        return "Canvas";
    }

    @Override
    public String adminCommandName() {
        return "canvaslocaltps";
    }

    @Override
    public String reloadPermission() {
        return "canvaslocaltps.reload";
    }
}
