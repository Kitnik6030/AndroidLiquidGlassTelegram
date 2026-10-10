
package com.kyant.backdrop.catalog.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.shapes.Capsule
import com.kyant.backdrop.catalog.CatalogDestination

private val Background = Color(0xFFF4F4F7)
private val MainText = Color(0xFF171719)
private val SecondaryText = Color(0xFF85858B)
private val Accent = Color(0xFF0088FF)

@Composable
fun HomeContent(onNavigate: (CatalogDestination) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(2) }
    val backdrop = rememberLayerBackdrop()

    val titles = listOf("Контакты", "Звонки", "Чаты", "Настройки")
    val symbols = listOf("♙", "☎", "☷", "⚙")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .pointerInput(selectedTab) {
                    var totalDrag = 0f
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, amount ->
                            totalDrag += amount
                        },
                        onDragEnd = {
                            if (totalDrag < -80f) {
                                selectedTab = (selectedTab + 1).coerceAtMost(3)
                            } else if (totalDrag > 80f) {
                                selectedTab = (selectedTab - 1).coerceAtLeast(0)
                            }
                            totalDrag = 0f
                        },
                        onDragCancel = {
                            totalDrag = 0f
                        }
                    )
                }
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 32.dp, bottom = 110.dp)
        ) {
            BasicText(
                text = titles[selectedTab],
                style = TextStyle(
                    color = MainText,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(22.dp))

            when (selectedTab) {
                0 -> repeat(12) { index ->
                    ContactRow(
                        "Контакт ${index + 1}",
                        "был(а) недавно"
                    )
                }

                1 -> repeat(8) { index ->
                    ContactRow(
                        "Контакт ${index + 1}",
                        "Недавний звонок"
                    )
                }

                2 -> repeat(12) { index ->
                    ContactRow(
                        "Собеседник ${index + 1}",
                        "Последнее сообщение"
                    )
                }

                3 -> listOf(
                    "Оформление",
                    "Уведомления",
                    "Конфиденциальность",
                    "Данные и память",
                    "О приложении"
                ).forEach { setting ->
                    ContactRow(setting, "")
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.dp)
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { Capsule() },
                    effects = {
                        vibrancy()
                        blur(8.dp.toPx())
                        lens(24.dp.toPx(), 24.dp.toPx())
                    },
                    onDrawSurface = {
                        drawRect(Color.White.copy(alpha = 0.45f))
                    }
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            titles.forEachIndexed { index, title ->
                val selected = selectedTab == index

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(Capsule())
                        .background(
                            if (selected) {
                                Color.White.copy(alpha = 0.65f)
                            } else {
                                Color.Transparent
                            }
                        )
                        .clickable {
                            selectedTab = index
                        }
                        .padding(vertical = 7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    BasicText(
                        text = symbols[index],
                        style = TextStyle(
                            color = if (selected) Accent else SecondaryText,
                            fontSize = 22.sp
                        )
                    )

                    Spacer(Modifier.height(3.dp))

                    BasicText(
                        text = title,
                        style = TextStyle(
                            color = if (selected) Accent else SecondaryText,
                            fontSize = 10.sp,
                            fontWeight = if (selected) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactRow(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.85f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFE0E8F5)),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = title.take(1),
                style = TextStyle(
                    color = Color(0xFF375A8A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            BasicText(
                text = title,
                style = TextStyle(
                    color = MainText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            if (subtitle.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                BasicText(
                    text = subtitle,
                    style = TextStyle(
                        color = SecondaryText,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
}
