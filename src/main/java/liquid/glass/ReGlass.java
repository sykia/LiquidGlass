package liquid.glass;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReGlass implements ModInitializer {
    public static final String MOD_ID = "reglass";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Init ReGlass library core");
    }
}
