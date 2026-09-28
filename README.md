[![Maven Central](https://img.shields.io/maven-central/v/io.github.valentinerutto/orbmotion?logo=apachemaven&label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.valentinerutto/orbmotion)

# OrbMotion

**OrbMotion is a Kotlin Multiplatform animation library for expressive AI and agent activity indicators, built with Compose Multiplatform for Android and iOS.**

It provides customizable animated orbs for AI assistants, agents, and interactive interfaces, with support for different states, sizes, speeds, and colors.

Build once with shared Compose UI and reuse the same animations across your Android and iOS applications.

## Installation

[OrbMotion](https://central.sonatype.com/artifact/io.github.valentinerutto/orbmotion) is published to Maven Central.

For  Kotlin Multiplatform projects, add the dependency to your `commonMain` source set:

```kotlin



```toml
[versions]
orbmotion = "1.0.1"

[libraries]
orbmotion = { module = "io.github.valentinerutto:orbmotion", version.ref = "orbmotion" }

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.orbmotion)
        }
    }
}
```


## UI Preview of Supported `OrbState` values:

| | | |
| :---: | :---: | :---: |
| **SEARCHING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/6ceb9b90-1f0b-45be-bc13-15ed813044f5" /> | **COMPOSING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/e084e746-4001-4cd6-8127-81b868d7df5f" /> | **SOLVING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/74a1414f-784e-4a15-bfd0-272eb1013f99" /> |
| **WEAVING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/2fc7ee2f-ed71-42c8-9ef1-76d33d84a6e1" /> | **BREATHING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/6f03124d-876a-4daf-a9da-91123c51a3ec" /> | **SHAPING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/8c006bf8-3056-4a10-afb8-a3ae9d938a9b" /> |
| **LISTENING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/8429b6b9-bb27-4d9b-bff4-1a0f56b6f88a" /> | **WORKING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/4c5cb335-b176-42b4-87c6-2de9b2122557" /> | **CONNECTING**<br><img width="250" height="250" src="https://github.com/user-attachments/assets/bd4b58a9-7281-495b-8db5-da33828a7aaa" /> |


## Usage

This library exposes a single public composable for consumers: `AnimatedThinkingOrb`. It internally drives animation timing so you don't need to manage `elapsedSeconds` yourself.

Basic example:

```kotlin
@Composable
fun ThinkingIndicator() {
    AnimatedThinkingOrb(
        state = OrbState.WEAVING,
        size = 120f,
        speed = 1f,
        color = Color.White
    )
}
```


## Customization

`AnimatedThinkingOrb` can be customized using four main properties:

- **`state`** — choose the animation state, e.g. `OrbState.WEAVING`
- **`size`** — control the orb size 
- **`speed`** — adjust the animation speed
- **`color`** — set the orb color

### Orb Size

The orb size can be customized using the following options:

- `OrbSize.Large`
- `OrbSize.Small`
- `OrbSize.Custom(36.dp)`
- Any `Float` value, e.g. `120f`



## Credit & Attribution

**OrbMotion is an unofficial Kotlin/Compose Multiplatform adaptation inspired by [Thinking Orbs](https://libraries.dev/orbs), originally created by [Jakub Antalik](https://github.com/Jakubantalik).**

The original *Thinking Orbs* is a dotted, canvas-based web animation library featuring expressive animation states.

OrbsMotion-kmp brings the core visual concept and animation ideas of *Thinking Orbs* to **Kotlin and Compose Multiplatform**, adapting the original Canvas-based approach to Compose's `Canvas` and `DrawScope` APIs.

The animations have also been **independently adapted and modified for this implementation**, including changes to animation behavior, timing, movement, and visual characteristics. 

### Original Project

* **Thinking Orbs:** [libraries.dev/orbs](https://libraries.dev/orbs)
* **Source:** [github.com/Jakubantalik/thinking-orbs](https://github.com/Jakubantalik/thinking-orbs)
* **Author:** Jakub Antalik
* **License:** MIT

### Attribution

The original *Thinking Orbs* project and its original animation concepts are credited to **Jakub Antalik**.

**Original project and animation concepts:** © Jakub Antalik
**Kotlin/Compose Multiplatform adaptation and modifications:** © Valentine Rutto

OrbsMotion-kmp is an **independent, unofficial project** and is not affiliated with, sponsored by, or endorsed by Jakub Antalik.

If you're looking for the original web implementation, please visit the [Original Thinking Orbs](https://libraries.dev/orbs) project.


## License

MIT © Valentine Rutto — see [`LICENSE`](LICENSE).

The original *Thinking Orbs* project is MIT © Jakub Antalik. The original MIT license and required copyright notice are preserved in [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md).
