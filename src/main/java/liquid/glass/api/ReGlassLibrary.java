package liquid.glass.api;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import liquid.glass.api.config.ReGlassConfig;
import liquid.glass.api.config.ReGlassConfigCallback;
import liquid.glass.api.config.ReGlassConfigurator;

public final class ReGlassLibrary {
    public static final String CONFIGURATOR_ENTRYPOINT = "reglass:configurator";

    private static final Logger LOGGER = LoggerFactory.getLogger("reglass");
    private static boolean bootstrapped;

    private ReGlassLibrary() {}

    public static ReGlassConfig config() {
        return ReGlassConfig.INSTANCE;
    }

    public static synchronized void bootstrapClient() {
        if (bootstrapped) {
            return;
        }

        reloadConfiguration();
        bootstrapped = true;
        LOGGER.info("ReGlass library client initialized");
    }

    public static synchronized void reloadConfiguration() {
        ReGlassConfig config = ReGlassConfig.INSTANCE;
        config.resetToDefaults();

        for (EntrypointContainer<ReGlassConfigurator> container :
                FabricLoader.getInstance().getEntrypointContainers(CONFIGURATOR_ENTRYPOINT, ReGlassConfigurator.class)) {
            try {
                container.getEntrypoint().configure(config);
            } catch (Exception e) {
                LOGGER.error("Failed to apply ReGlass configurator from {}", container.getProvider().getMetadata().getId(), e);
            }
        }

        ReGlassConfigCallback.EVENT.invoker().configure(config);
    }
}
