package liquid.glass.api.config;

import java.util.HashSet;
import java.util.Set;
import org.joml.Vector2f;
import liquid.glass.api.config.model.RimLight;

public final class ReGlassConfig {
    public static final ReGlassConfig INSTANCE = new ReGlassConfig();

    public final Set<String> classWhitelist = new HashSet<>();
    public final Set<String> classBlacklist = new HashSet<>();

    public int defaultTintColor = 0x000000;
    public float defaultTintAlpha = 0.0f;

    public float defaultSmoothing = 0.003f;
    public int defaultBlurRadius = 12;

    public float defaultShadowExpand = 30.0f;
    public float defaultShadowFactor = 0.25f;
    public float defaultShadowOffsetX = 0.0f;
    public float defaultShadowOffsetY = 2.0f;
    public int defaultShadowColor = 0x000000;
    public float defaultShadowColorAlpha = 1.0f;

    public float defaultRefThickness = 20.0f;
    public float defaultRefFactor = 1.4f;
    public float defaultRefDispersion = 7.0f;
    public float defaultRefFresnelRange = 30.0f;
    public float defaultRefFresnelHardness = 20.0f;
    public float defaultRefFresnelFactor = 20.0f;

    public float defaultGlareRange = 30.0f;
    public float defaultGlareHardness = 20.0f;
    public float defaultGlareConvergence = 50.0f;
    public float defaultGlareOppositeFactor = 80.0f;
    public float defaultGlareFactor = 90.0f;
    public float defaultGlareAngleRad = (float) (-45.0 * Math.PI / 180.0);

    public RimLight rimLight = new RimLight(new Vector2f(-1.0f, 1.0f).normalize(), 0xFFFFFF, 0.1f);

    public float pixelEpsilon = 2.0f;
    public float debugStep = 9.0f;
    public boolean pixelatedGrid = false;
    public float pixelatedGridSize = 8.0f;

    public float hoverScalePx = 1.5f;
    public float focusScalePx = 2.5f;
    public float focusBorderWidthPx = 2.0f;
    public float focusBorderIntensity = 0.75f;
    public float focusBorderSpeed = 1.6f;

    private ReGlassConfig() {}

    public synchronized void resetToDefaults() {
        classWhitelist.clear();
        classBlacklist.clear();

        defaultTintColor = 0x000000;
        defaultTintAlpha = 0.0f;
        defaultSmoothing = 0.003f;
        defaultBlurRadius = 12;

        defaultShadowExpand = 30.0f;
        defaultShadowFactor = 0.25f;
        defaultShadowOffsetX = 0.0f;
        defaultShadowOffsetY = 2.0f;
        defaultShadowColor = 0x000000;
        defaultShadowColorAlpha = 1.0f;

        defaultRefThickness = 20.0f;
        defaultRefFactor = 1.4f;
        defaultRefDispersion = 7.0f;
        defaultRefFresnelRange = 30.0f;
        defaultRefFresnelHardness = 20.0f;
        defaultRefFresnelFactor = 20.0f;

        defaultGlareRange = 30.0f;
        defaultGlareHardness = 20.0f;
        defaultGlareConvergence = 50.0f;
        defaultGlareOppositeFactor = 80.0f;
        defaultGlareFactor = 90.0f;
        defaultGlareAngleRad = (float) (-45.0 * Math.PI / 180.0);

        rimLight = new RimLight(new Vector2f(-1.0f, 1.0f).normalize(), 0xFFFFFF, 0.1f);
        pixelEpsilon = 2.0f;
        debugStep = 9.0f;
        pixelatedGrid = false;
        pixelatedGridSize = 8.0f;
        hoverScalePx = 1.5f;
        focusScalePx = 2.5f;
        focusBorderWidthPx = 2.0f;
        focusBorderIntensity = 0.75f;
        focusBorderSpeed = 1.6f;
    }

    public synchronized boolean isClassExcluded(Class<?> klass) {
        String name = klass.getName();
        if (!classWhitelist.isEmpty()) {
            return !classWhitelist.contains(name);
        }
        return classBlacklist.contains(name);
    }
}
