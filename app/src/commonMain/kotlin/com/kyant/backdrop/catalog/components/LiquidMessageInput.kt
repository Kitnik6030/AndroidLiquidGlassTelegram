
package com.kyant.backdrop.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.shapes.Capsule

@Composable
fun LiquidMessageInput(
    backdrop: Backdrop,
    onSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlassCircleButton(
            backdrop = backdrop,
            symbol = "⌁",
            onClick = {}
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = 54.dp)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { Capsule() },
                    effects = {
                        vibrancy()
                        blur(8.dp.toPx())
                        lens(16.dp.toPx(), 24.dp.toPx())
                    },
                    onDrawSurface = {
                        drawRect(Color(0xFF202020).copy(alpha = 0.78f))
                    }
                )
                .padding(start = 16.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp
                ),
                cursorBrush = androidx.compose.ui.graphics.SolidColor(
                    Color(0xFF4D9CFF)
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (text.isEmpty()) {
                            BasicText(
                                text = "Сообщение",
                                style = TextStyle(
                                    color = Color(0xFF858585),
                                    fontSize = 17.sp
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            BasicText(
                text = "◔",
                modifier = Modifier.clickable { },
                style = TextStyle(
                    color = Color(0xFFAAAAAA),
                    fontSize = 27.sp
                )
            )
        }

        GlassCircleButton(
            backdrop = backdrop,
            symbol = if (text.isBlank()) "♩" else "➤",
            onClick = {
                val message = text.trim()
                if (message.isNotEmpty()) {
                    onSend(message)
                    text = ""
                }
            }
        )
    }
}

@Composable
private fun GlassCircleButton(
    backdrop: Backdrop,
    symbol: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy()
                    blur(8.dp.toPx())
                    lens(14.dp.toPx(), 22.dp.toPx())
                },
                onDrawSurface = {
                    drawRect(Color(0xFF303030).copy(alpha = 0.68f))
                }
            )
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = symbol,
            style = TextStyle(
                color = Color.White,
                fontSize = 27.sp
            )
        )
    }
}
