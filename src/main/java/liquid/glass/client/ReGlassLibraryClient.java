package liquid.glass.client;

import net.fabricmc.api.ClientModInitializer;
import liquid.glass.api.ReGlassLibrary;

public class ReGlassLibraryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ReGlassLibrary.bootstrapClient();
    }
}
