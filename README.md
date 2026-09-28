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
  <img width="800" height="1777" alt="searching-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/ac91abce-d27a-4ba1-8006-65bc59a77e70" />

- `COMPOSING`
<img width="800" height="1777" alt="composing-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/243caef6-3fc5-4e79-9526-24df4cbe5a39" />

- `SOLVING`
  <img width="800" height="1777" alt="solving-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/4191610a-deef-44b9-96f0-4c7d684ada12" />

- `LISTENING`
  <img width="800" height="1777" alt="listening-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/cf338d1b-4d87-4ca8-946f-4f1dce17753c" />

- `WORKING`
  <img width="800" height="1777" alt="working-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/cd0428e3-0a8c-473b-84fb-fc787fb95e98" />

  
- `CONNECTING`
<img width="800" height="1777" alt="connecting-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/6de0f4e4-b66d-45c2-8862-48adc3bfde06" />

- `WEAVING`
  <img width="800" height="1777" alt="weaving-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/4e44cb62-2da1-4e01-b67b-ddf6f37f4b8e" />

- `BREATHING`
<img width="800" height="1777" alt="breathing-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/0ea0dbab-6d73-49eb-bce8-b33e73a71cda" />

- `SHAPING`
  <img width="800" height="1777" alt="shaping-vid-ezgif com-video-to-gif-converter" src="https://github.com/user-attachments/assets/3e1b9df8-f539-4561-b49e-102126c12dce" />


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
