package com.valentinerutto.orbmotion.orbs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OrbAnimationPlaygroundScreen(modifier: Modifier = Modifier) {
    var elapsed by remember { mutableFloatStateOf(0f) }
    var selectedState by remember { mutableStateOf(OrbState.SEARCHING) }
    var orbSize by remember { mutableFloatStateOf(360f) }
    var speed by remember { mutableFloatStateOf(1f) }
    var orbColor by remember { mutableStateOf(Color.White) }
    var darkTheme by remember { mutableStateOf(true) }
    var showSnippetSheet by remember { mutableStateOf(false) }

    val generatedSnippet = remember(selectedState, orbSize, speed, orbColor, elapsed) {
        buildOrbCodeSnippet(
            state = selectedState,
            size = orbSize,
            speed = speed,
            color = orbColor,
        )
    }

    val clipboard = LocalClipboardManager.current
    val background = Color.Black
    val textColor = Color.White
    val panelColor = Color(0xFF1A1A1A)
    val trackColor = Color(0xFF5A5A5A)
    val activeTrackColor = Color.White
    val thumbColor = Color.White

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            elapsed += 0.016f
        }
    }

    Surface(color = background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Orb Playground",
                    color = textColor,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val availableSize = minOf(maxWidth.value, maxHeight.value)
                    val displayOrbSize =
                        (orbSize.coerceAtMost(availableSize * 0.5f)).coerceAtLeast(120f)

                    AnimatedThinkingOrb(
                        modifier = Modifier.size(displayOrbSize.dp),
                        state = selectedState,
                        size = orbSize,
                        speed = speed,
                      //  elapsedSeconds = elapsed,
                        color = orbColor,
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select state",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OrbState.values().forEach { st ->
                        StateChip(
                            label = st.name,
                            selected = st == selectedState,
                            onClick = { selectedState = st }
                        )
                    }
                }

                Surface(
                    color = panelColor,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Size", color = textColor, fontSize = 16.sp)
                            Text("${orbSize.toInt()}", color = textColor, fontSize = 16.sp)
                        }

                        Slider(
                            value = orbSize,
                            onValueChange = { orbSize = it },
                            valueRange = 64f..360f,
                            modifier = Modifier.height(18.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = thumbColor,
                                activeTrackColor = activeTrackColor,
                                inactiveTrackColor = trackColor,
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Speed", color = textColor, fontSize = 16.sp)
                            Text("${formatFloat(speed)}x", color = textColor, fontSize = 16.sp)
                        }

                        Slider(
                            value = speed,
                            onValueChange = { speed = it },
                            valueRange = 0.1f..4f,
                            modifier = Modifier.height(18.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = thumbColor,
                                activeTrackColor = activeTrackColor,
                                inactiveTrackColor = trackColor,
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Color Palette",
                                color = textColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            SelectionContainer {
                                Text(
                                    text = "#${orbColor.toArgb().toUInt().toString(16).uppercase().padStart(6, '0')}",
                                    color = textColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val colors = listOf(
                                Color.White,
                                Color(0xFF3DB7F4),
                                Color(0xFFB388FF),
                                Color(0xFFFF4C9A),
                                Color(0xFFFF7AB6),
                                Color(0xFFFFD93D),
                                Color(0xFFFFB14A),
                                Color(0xFF39D98A),
                                Color(0xFFFF4D4D),
                                Color(0xFFFF6F61),
                                Color(0xFF8B5CF6),
                                Color(0xFF9EE7FF)
                            )

                            colors.forEach { c ->
                                val selected = orbColor == c
                                Box(
                                    modifier = Modifier
                                        .size(if (selected) 34.dp else 30.dp)
                                        .clip(CircleShape)
                                        .background(c)
                                        .clickable { orbColor = c }
                                        .then(
                                            if (selected) Modifier.border(
                                                2.dp,
                                                Color.White,
                                                CircleShape
                                            ) else Modifier
                                        )
                                )
                            }
                        }
                    }
                }

                Surface(
                    color = Color(0xFF1E1E1E),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        .clickable { showSnippetSheet = true }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF262626))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Show Code Snippet",
                                color = Color(0xFFB0B0B0),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }


    if (showSnippetSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSnippetSheet = false },
            containerColor = Color(0xFF1E1E1E),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Code snippet",
                        color = textColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(
                        onClick = {
                            clipboard.setText(AnnotatedString(generatedSnippet))
                        }
                    ) {
                        Text("Copy", color = Color(0xFF7CC3FF))
                    }
                }


                    val scrollState = rememberScrollState()

                    val annotated = remember(generatedSnippet) { highlightKotlin(generatedSnippet) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 220.dp)
                            .background(Color(0xFF1E1E1E))
                            .padding(12.dp)
                            .verticalScroll(scrollState)
                    ) {
                        androidx.compose.material3.Text(
                            text = annotated,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp
                            ),
                            overflow = TextOverflow.Visible,
                        )
                    }
                }
            }
        }
    }
}

private fun highlightKotlin(code: String): AnnotatedString {
    val keywords = setOf(
        "fun", "val", "var", "return", "if", "else", "when", "for", "while",
        "true", "false", "null", "import", "package", "class", "object", "interface"
    )

    val keywordStyle = SpanStyle(color = Color(0xFF569CD6))
    val typeStyle = SpanStyle(color = Color(0xFFD7BA7D))
    val functionStyle = SpanStyle(color = Color(0xFF9CDCFE))
    val numberStyle = SpanStyle(color = Color(0xFFB5CEA8))
    val stringStyle = SpanStyle(color = Color(0xFFCE9178))
    val defaultStyle = SpanStyle(color = Color(0xFFEAEAEA))

    // Tokenizer regex with capture groups:
    // 1 -> string literal, 2 -> number, 3 -> identifier, 4 -> whitespace, 5 -> other (punctuation)
    val tokenRegex = Regex("(\\\"(?:\\\\.|[^\\\\\"])*\\\")|(\\b\\d+\\.?\\d*\\b)|(\\b[A-Za-z_][A-Za-z0-9_]*\\b)|(\\s+)|([^\\sA-Za-z0-9_\\\"])")

    return buildAnnotatedString {
        val matches = tokenRegex.findAll(code)
        for (m in matches) {
            val (strLit, number, ident, space, other) = m.destructured

            when {
                strLit.isNotEmpty() -> pushStyle(stringStyle)
                number.isNotEmpty() -> pushStyle(numberStyle)
                ident.isNotEmpty() -> {
                    val token = ident
                    if (keywords.contains(token)) pushStyle(keywordStyle)
                    else if (token.firstOrNull()?.isUpperCase() == true) pushStyle(typeStyle)
                    else {
                        // Peek next non-whitespace character to detect function calls
                        val nextIndex = m.range.last + 1
                        var isFunction = false
                        if (nextIndex < code.length) {
                            var j = nextIndex
                            while (j < code.length && code[j].isWhitespace()) j++
                            if (j < code.length && code[j] == '(') isFunction = true
                        }
                        if (isFunction) pushStyle(functionStyle) else pushStyle(defaultStyle)
                    }
                }
                space.isNotEmpty() -> pushStyle(defaultStyle)
                other.isNotEmpty() -> pushStyle(defaultStyle)
                else -> pushStyle(defaultStyle)
            }

            append(m.value)
            pop()
        }
    }
}






@Composable
private fun StateChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val container = if (selected) Color.White else Color(0xFF2A3347)
    val content = if (selected) Color.Black else Color.White

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content
        ),
        modifier = Modifier.height(42.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun buildOrbCodeSnippet(
    state: OrbState,
    size: Float,
    speed: Float,
    color: Color,
): String {
    val hex = color.toArgb().toUInt().toString(16).uppercase().padStart(8, '0')
    return buildString {
        appendLine("AnimatedThinkingOrb(")
        appendLine("    modifier = Modifier.size(${size.toInt()}.dp),")
        appendLine("    state = OrbState.${state.name},")
        appendLine("    size = ${formatFloat(size)},")
        appendLine("    speed = ${formatFloat(speed)},")
            append("    color = Color(0x${hex}),")
        appendLine()
        append(")")
    }
}

fun formatFloat(value: Float): String {
    val scaled = (value * 100f).toInt()
    val whole = scaled / 100
    val fraction = kotlin.math.abs(scaled % 100)
    return "${whole}.${fraction.toString().padStart(2, '0')}f"
}