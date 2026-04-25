package liquid.glass.api.config;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@FunctionalInterface
public interface ReGlassConfigCallback {
    Event<ReGlassConfigCallback> EVENT = EventFactory.createArrayBacked(
            ReGlassConfigCallback.class,
            listeners -> config -> {
                for (ReGlassConfigCallback listener : listeners) {
                    listener.configure(config);
                }
            }
    );

    void configure(ReGlassConfig config);
}
