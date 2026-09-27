import SwiftUI
import Shared

// SwiftUI wrapper that embeds the Kotlin/Compose UIViewController for the AnimatedThinkingOrb.
// Uses the Kotlin/Native interop function `makeOrbLoadingIndicatorViewController`.

struct AnimatedComposeOrbView: UIViewControllerRepresentable {
    var stateOrdinal: Int32 = 0 // OrbState index (e.g., 0 = SEARCHING)
    var sizeDp: Float = 64.0
    var speed: Float = 1.0
    var colorArgb: Int64 = 0xFFFFFFFF

    func makeUIViewController(context: Context) -> UIViewController {
        return OrbIosInteropKt.makeOrbLoadingIndicatorViewController(
            stateOrdinal: Int32(stateOrdinal),
            sizeDp: sizeDp,
            speed: speed,
            colorArgb: colorArgb
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // No-op. To update parameters at runtime you'd expose a Kotlin entry that updates state.
    }
}

struct AnimatedComposeOrbView_Previews: PreviewProvider {
    static var previews: some View {
        AnimatedComposeOrbView()
            .frame(width: 200, height: 200)
            .previewLayout(.sizeThatFits)
    }
}
