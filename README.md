# OrbsMotion-kmp

Animated, monochrome “thinking orb” indicators for AI and agent UIs — built with **Kotlin Compose Multiplatform** for Android and iOS.

## Installation

Add the library to your Gradle module (Kotlin DSL):

```
implementation("io.github.valentinerutto:orbmotion:1.0.1")
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
  Column(
                    modifier = Modifier.fillMaxWidth().fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    
                    var selectedState by remember { mutableStateOf(OrbState.WEAVING) }
                    var orbSize by remember { mutableFloatStateOf(360f) }
                    var speed by remember { mutableFloatStateOf(1f) }
                    var orbColor by remember { mutableStateOf(Color.White) }


                    AnimatedThinkingOrb(
                        modifier = Modifier.size(120.dp),
                        state = selectedState,
                        size = orbSize,
                        speed = speed,
                        color = orbColor
                    )
                    

            }
```


## Supported `OrbSize` values:
- `OrbSize.Large`
- `OrbSize.Small`
- `OrbSize.Custom(36.dp)`
- `any float value like: 120f,`


If you need lower-level control (for embedding in custom rendering loops), the library contains an internal `ThinkingOrb` composable which accepts an `elapsedSeconds` parameter — but this is not part of the public API surface by default.

If you'd like the library to expose the lower-level API, or to provide alternate wrappers (e.g., frame-synced vs. time-synced variants), open an issue or submit a PR.

## iOS Usage

The shared module exposes lightweight interop helpers to embed the Compose UI in iOS apps. The easiest entry points are the Kotlin/Native top-level functions that return a `UIViewController`.

Example (UIKit):

```swift
// Call the iOS interop function exported by the shared framework
let orbVC = OrbIosInteropKt.makeOrbLoadingIndicatorViewController(
    stateOrdinal: 0,    // OrbState index (e.g., 0 = SEARCHING)
    sizeDp: 64,
    speed: 1.0,
    colorArgb: 0xFFFFFFFF
)
addChild(orbVC)
orbVC.view.frame = view.bounds
view.addSubview(orbVC.view)
orbVC.didMove(toParent: self)
```

Example (SwiftUI):

```swift
import SwiftUI

struct OrbViewControllerWrapper: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        OrbIosInteropKt.makeOrbLoadingIndicatorViewController(stateOrdinal: 0, sizeDp: 64, speed: 1.0, colorArgb: 0xFFFFFFFF)
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        OrbViewControllerWrapper()
            .edgesIgnoringSafeArea(.all)
    }
}
```

Notes:
- The helper `makeOrbLoadingIndicatorViewController` returns a `UIViewController` embedding `AnimatedThinkingOrb`.
- `stateOrdinal` maps to `OrbState.entries` (use ordinal numbers or add your own Swift enum wrapper).
- `colorArgb` is an ARGB hex value (e.g., `0xFFFFFFFF` for white).
- If you need more Swift-friendly APIs (e.g., `UIColor` parameters), I can add convenience wrappers in `shared/src/iosMain`.

## Credit & Attribution

**OrbsMotion-kmp is an unofficial Kotlin/Compose Multiplatform port of [Thinking Orbs](https://libraries.dev/orbs) by [Jakub Antalik](https://github.com/Jakubantalik).**

The original *Thinking Orbs* is a dotted, canvas-based web animation library featuring expressive states such as `working`, `searching`, `solving`, `listening`, `composing`, and `shaping`.

This project ports the original animation concepts and implementations from JavaScript/Canvas to **Kotlin and Compose Multiplatform**, adapting the rendering and animation logic to Compose's `Canvas` and `DrawScope` APIs.

Original project:

* **Thinking Orbs:** https://libraries.dev/orbs
* **Source:** https://github.com/Jakubantalik/thinking-orbs
* **Author:** Jakub Antalik
* **License:** MIT

The original library features expressive states such as `SEARCHING`, `COMPOSING`, `SOLVING`, `LISTENING`, `WORKING`, and `SHAPING`.

This Kotlin/Compose Multiplatform port additionally includes `BREATHING`, `CONNECTING`, and `WEAVING`.

**Original animation design and implementation:** © Jakub Antalik
**Kotlin/Compose Multiplatform port:** © Valentine Rutto

This project is **not affiliated with or endorsed by Jakub Antalik**.

If you're building for the web, please use the [Original Thinking Orbs](https://libraries.dev/orbs).


## License

MIT © Valentine Rutto — see [`LICENSE`](LICENSE).

The original *Thinking Orbs* project is MIT © Jakub Antalik. The original MIT license and required copyright notice are preserved in [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md).
