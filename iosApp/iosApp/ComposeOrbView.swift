import SwiftUI
import Shared

// SwiftUI wrapper that embeds the Kotlin/Compose UIViewController for the Orb gallery.
// Kotlin/Native exports the iOS bridge functions through this generated facade.

struct ComposeOrbView: UIViewControllerRepresentable {
    var speed: Float = 1.0
    var sizeDp: Float = 48.0
    var themeOrdinal: Int = 1 // 0=Auto,1=Light,2=Dark
    var dotColorArgb: Int64 = 0xFFFFFFFF
    var bgColorArgb: Int64 = 0x00000000

    func makeUIViewController(context: Context) -> UIViewController {
        return OrbIosInteropKt.makeThinkingOrbGalleryViewController(
            speed: speed,
            sizeDp: sizeDp,
            themeOrdinal: Int32(themeOrdinal),
            dotColorArgb: dotColorArgb,
            bgColorArgb: bgColorArgb
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // No-op for now. To update params dynamically, provide a Kotlin entry that updates state.
    }
}

struct ComposeOrbView_Previews: PreviewProvider {
    static var previews: some View {
        ComposeOrbView()
    }
}
