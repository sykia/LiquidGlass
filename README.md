# LiquidGlass для Fabric 1.21.11

LiquidGlass — это клиентская библиотека стеклянного GUI-рендера для Fabric-модов.

Библиотека не добавляет свои меню, конфиги, экраны или горячие клавиши. Она нужна как API-слой, который вы вызываете из своего мода.

## Что умеет

- Рисовать liquid-glass элементы в ваших GUI
- Дать готовый стиль-билдер (`ReGlassStyle`)
- Дать готовый рендер-билдер (`ReGlassRenderer`)
- Дать готовый виджет (`LiquidGlassWidget`)
- Позволять внешним модам настраивать глобальный конфиг через entrypoint/event

## Требования

- Minecraft `1.21.11`
- Fabric Loader
- Fabric API
- Java `21`

## Установка

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    modImplementation "com.github.sykia:LiquidGlass:-SNAPSHOT"
    include "com.github.sykia:LiquidGlass:-SNAPSHOT"
}
```

### fabric.mod.json

```json
{
  "depends": {
    "fabricloader": ">=0.15.0",
    "fabric-api": "*",
    "minecraft": ">=1.21.11",
    "java": ">=21",
    "reglass": ">=2.0.0"
  }
}
```

## Быстрый старт

```java
import render.api.liquid.glass.ReGlassRenderer;
import render.api.liquid.glass.ReGlassStyle;

ReGlassRenderer.create(context)
        .dimensions(x, y, width, height)
        .cornerRadius(12.0f)
        .style(ReGlassStyle.create()
                .tint(0x000000, 0.18f)
                .blurRadius(12)
                .shadow(30f, 0.25f, 0f, 2f))
        .hover(isHovered ? 1f : 0f)
        .focus(isFocused ? 1f : 0f)
        .render();
```

## Настройка библиотеки из вашего мода

- Через кастомный entrypoint: `reglass:configurator`
- Через event: `ReGlassConfigCallback.EVENT`

Полное API-руководство: [API.md](API.md)