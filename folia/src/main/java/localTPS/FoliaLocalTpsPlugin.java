package localTPS;

public final class FoliaLocalTpsPlugin extends LocalTpsPlugin {
    @Override
    protected TpsProvider createTpsProvider() {
        return new FoliaTpsProvider();
    }

    @Override
    public String platformName() {
        return "Folia";
    }

    @Override
    public String adminCommandName() {
        return "folialocaltps";
    }

    @Override
    public String reloadPermission() {
        return "folialocaltps.reload";
    }
}
