package localTPS;

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
