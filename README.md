# OrbsMotion-kmp

Animated, monochrome “thinking orb” indicators for AI and agent UIs — built with **Kotlin Compose Multiplatform** for Android and iOS.

## Installation

Add the library to your Gradle module (Kotlin DSL):

```
implementation("io.github.valentinerutto:orbmotion:1.0.1")
```

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

Supported `OrbState` values:

- `SEARCHING`
- `COMPOSING`
  <img width="240" height="536" alt="composing" src="https://github.com/user-attachments/assets/6aaa2a5e-1e62-4a1b-8aa3-38b1b311bb42" />

- `SOLVING`
- `LISTENING`
- `WORKING`
- `CONNECTING`
- `WEAVING`
- `BREATHING`
  <img width="240" height="534" alt="breathing" src="https://github.com/user-attachments/assets/dcc11924-b2b2-47ce-90ee-c3efd1d38b14" />

- `SHAPING`

Supported `OrbSize` values:
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
