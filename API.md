# LiquidGlass API (полный справочник)

Этот файл описывает все публичные API-компоненты библиотеки на текущий момент.

## Содержание API

- `api.liquid.glass.ReGlassLibrary`
- `config.api.liquid.glass.ReGlassConfig`
- `config.api.liquid.glass.ReGlassConfigurator`
- `config.api.liquid.glass.ReGlassConfigCallback`
- `model.config.api.liquid.glass.RimLight`
- `render.api.liquid.glass.ReGlassRenderer`
- `render.api.liquid.glass.ReGlassStyle`
- `widget.api.liquid.glass.LiquidGlassWidget`

## 1) ReGlassLibrary

Пакет: `liquid.glass.api`

### Поля

- `public static final String CONFIGURATOR_ENTRYPOINT = "reglass:configurator"`

### Методы

- `public static ReGlassConfig config()`
Возвращает глобальный singleton-конфиг библиотеки (`ReGlassConfig.INSTANCE`).

- `public static synchronized void bootstrapClient()`
Инициализирует библиотеку на клиенте (однократно), вызывает загрузку конфигурации и внешних конфигураторов.

- `public static synchronized void reloadConfiguration()`
Сбрасывает конфиг в дефолт и повторно применяет:
1. Все `reglass:configurator` entrypoint-реализации.
2. Все слушатели `ReGlassConfigCallback.EVENT`.

## 2) ReGlassConfigurator

Пакет: `liquid.glass.api.config`

Функциональный интерфейс для настройки конфига через entrypoint.

### Метод

- `void configure(ReGlassConfig config)`

### Использование

`fabric.mod.json`:

```json
{
  "entrypoints": {
    "reglass:configurator": [
      "com.example.mymod.client.MyReGlassConfigurator"
    ]
  }
}
```

```java
import config.api.liquid.glass.ReGlassConfig;
import config.api.liquid.glass.ReGlassConfigurator;

public final class MyReGlassConfigurator implements ReGlassConfigurator {
    @Override
    public void configure(ReGlassConfig config) {
        config.defaultBlurRadius = 10;
        config.defaultTintAlpha = 0.18f;
    }
}
```

## 3) ReGlassConfigCallback

Пакет: `liquid.glass.api.config`

Функциональный интерфейс для runtime-регистрации конфигуратора через Fabric Event API.

### Поля

- `Event<ReGlassConfigCallback> EVENT`

### Методы

- `void configure(ReGlassConfig config)`

### Использование

```java
import config.api.liquid.glass.ReGlassConfigCallback;

ReGlassConfigCallback.EVENT.register(config -> {
    config.defaultBlurRadius = 8;
    config.defaultShadowFactor = 0.18f;
});
```

## 4) ReGlassConfig (полностью)

Пакет: `liquid.glass.api.config`

Глобальный mutable-конфиг библиотеки.

### Singleton

- `public static final ReGlassConfig INSTANCE`

### Фильтры классов

- `public final Set<String> classWhitelist = new HashSet<>()`
- `public final Set<String> classBlacklist = new HashSet<>()`

Логика:
- Если `classWhitelist` не пустой, разрешены только классы из whitelist.
- Если `classWhitelist` пустой, исключаются классы из `classBlacklist`.

### Поля стиля и рендера

- `public int defaultTintColor = 0x000000`
- `public float defaultTintAlpha = 0.0f`
- `public float defaultSmoothing = 0.003f`
- `public int defaultBlurRadius = 12`

#### Shadow

- `public float defaultShadowExpand = 30.0f`
- `public float defaultShadowFactor = 0.25f`
- `public float defaultShadowOffsetX = 0.0f`
- `public float defaultShadowOffsetY = 2.0f`
- `public int defaultShadowColor = 0x000000`
- `public float defaultShadowColorAlpha = 1.0f`

#### Refraction

- `public float defaultRefThickness = 20.0f`
- `public float defaultRefFactor = 1.4f`
- `public float defaultRefDispersion = 7.0f`
- `public float defaultRefFresnelRange = 30.0f`
- `public float defaultRefFresnelHardness = 20.0f`
- `public float defaultRefFresnelFactor = 20.0f`

#### Glare

- `public float defaultGlareRange = 30.0f`
- `public float defaultGlareHardness = 20.0f`
- `public float defaultGlareConvergence = 50.0f`
- `public float defaultGlareOppositeFactor = 80.0f`
- `public float defaultGlareFactor = 90.0f`
- `public float defaultGlareAngleRad = (float) (-45.0 * Math.PI / 180.0)`

#### Rim Light и Debug

- `public RimLight rimLight = new RimLight(new Vector2f(-1.0f, 1.0f).normalize(), 0xFFFFFF, 0.1f)`
- `public float pixelEpsilon = 2.0f`
- `public float debugStep = 9.0f`
- `public boolean pixelatedGrid = false`
- `public float pixelatedGridSize = 8.0f`

#### Interaction

- `public float hoverScalePx = 1.5f`
- `public float focusScalePx = 2.5f`
- `public float focusBorderWidthPx = 2.0f`
- `public float focusBorderIntensity = 0.75f`
- `public float focusBorderSpeed = 1.6f`

### Методы

- `public synchronized void resetToDefaults()`
Сброс всех полей к дефолтным значениям.

- `public synchronized boolean isClassExcluded(Class<?> klass)`
Проверка класса по whitelist/blacklist.

## 5) RimLight

Пакет: `liquid.glass.api.config.model`

Record:

```java
public record RimLight(Vector2f direction, int color, float intensity)
```

### Поля record

- `direction` — нормализованный вектор направления света.
- `color` — RGB (`int`).
- `intensity` — интенсивность.

### Константа

- `public static final RimLight DEFAULT`

## 6) ReGlassStyle

Пакет: `liquid.glass.api.render`

Mutable-билдер стиля. Если параметр не задан вручную, используются значения из `ReGlassConfig`/`ReGlassAnim`.

### Создание

- `public static ReGlassStyle create()`

### Setter-методы

- `ReGlassStyle tint(int color, float alpha)`
- `ReGlassStyle smoothing(float factor)`
- `ReGlassStyle blurRadius(int radius)`
- `ReGlassStyle shadow(float expand, float factor, float offsetX, float offsetY)`
- `ReGlassStyle shadowColor(int color, float alpha)`
- `ReGlassStyle refractionThickness(float v)`
- `ReGlassStyle refractionFactor(float v)`
- `ReGlassStyle refractionDispersion(float v)`
- `ReGlassStyle fresnelRange(float v)`
- `ReGlassStyle fresnelHardness(float v)`
- `ReGlassStyle fresnelFactor(float v)`
- `ReGlassStyle glareRange(float v)`
- `ReGlassStyle glareHardness(float v)`
- `ReGlassStyle glareConvergence(float v)`
- `ReGlassStyle glareOppositeFactor(float v)`
- `ReGlassStyle glareFactor(float v)`
- `ReGlassStyle glareAngleRad(float v)`

### Getter-методы

- `int getTintColor()`
- `float getTintAlpha()`
- `float getSmoothing()`
- `int getBlurRadius()`
- `float getShadowExpand()`
- `float getShadowFactor()`
- `float getShadowOffsetX()`
- `float getShadowOffsetY()`
- `int getShadowColor()`
- `float getShadowColorAlpha()`
- `float getRefThickness()`
- `float getRefFactor()`
- `float getRefDispersion()`
- `float getRefFresnelRange()`
- `float getRefFresnelHardness()`
- `float getRefFresnelFactor()`
- `float getGlareRange()`
- `float getGlareHardness()`
- `float getGlareConvergence()`
- `float getGlareOppositeFactor()`
- `float getGlareFactor()`
- `float getGlareAngleRad()`

## 7) ReGlassRenderer

Пакет: `liquid.glass.api.render`

Основной API рендера liquid-glass элемента.

### Старт

- `public static ReGlassRenderer.Builder create(DrawContext context)`

### Builder (полностью)

- `Builder fromWidget(ClickableWidget widget)`
Берет позицию, размеры и текст из виджета.

- `Builder position(int x, int y)`
- `Builder size(int width, int height)`
- `Builder dimensions(int x, int y, int width, int height)`
- `Builder cornerRadius(float radius)`
- `Builder text(Text text)`
- `Builder style(ReGlassStyle style)`
- `Builder hover(float amount)`
- `Builder focus(float amount)`
- `Builder selected(float amount)`
Алиас для `focus`.

- `void render()`
Добавляет элемент в GUI render-state для отрисовки liquid-glass проходом.

### Важные детали

- `hover` и `focus` клампятся в диапазон `[0..1]`.
- Если `cornerRadius` не задан, используется `0.5 * min(width, height)`.

## 8) LiquidGlassWidget

Пакет: `liquid.glass.api.widget`

Готовый `ClickableWidget`, который сам вызывает ReGlass-рендер в `renderWidget`.

### Поля

- `public ReGlassStyle style = new ReGlassStyle()`

### Конструктор

- `public LiquidGlassWidget(int x, int y, int width, int height, ReGlassStyle style)`

### Методы

- `public LiquidGlassWidget setCornerRadiusPx(float radiusPx)`
- `public LiquidGlassWidget setMoveable(boolean moveable)`

### Поведение drag

Если `moveable=true`, виджет можно перетаскивать ЛКМ.

## Полный пример интеграции

```java
import net.fabricmc.api.ClientModInitializer;
import config.api.liquid.glass.ReGlassConfigCallback;

public final class MyClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ReGlassConfigCallback.EVENT.register(config -> {
            config.defaultBlurRadius = 9;
            config.defaultTintAlpha = 0.15f;
            config.defaultShadowFactor = 0.2f;
            config.focusBorderIntensity = 0.8f;
        });
    }
}
```

```java
import render.api.liquid.glass.ReGlassRenderer;
import render.api.liquid.glass.ReGlassStyle;

ReGlassRenderer.create(context)
        .dimensions(20, 20, 160, 40)
        .cornerRadius(10f)
        .style(ReGlassStyle.create()
                .tint(0x000000, 0.2f)
                .blurRadius(12)
                .fresnelFactor(18f)
                .glareFactor(70f))
        .hover(hovered ? 1f : 0f)
        .focus(focused ? 1f : 0f)
        .render();
```

## Версия API

Актуально для ветки библиотеки с `mod_version=2.0.0`.