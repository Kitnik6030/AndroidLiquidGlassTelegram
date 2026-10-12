
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

import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.catalog.CatalogDestination
import com.kyant.backdrop.catalog.components.LiquidBottomTab
import com.kyant.backdrop.catalog.components.LiquidBottomTabs
import com.kyant.backdrop.catalog.components.LiquidMessageInput

private val Background = Color(0xFF000000)
private val MainText = Color(0xFFFFFFFF)
private val SecondaryText = Color(0xFF85858B)
private val Accent = Color(0xFF0088FF)

private data class ChatMessage(
    val text: String,
    val mine: Boolean
)

@Composable
fun HomeContent(onNavigate: (CatalogDestination) -> Unit) {
    var selectedTab by remember { mutableIntStateOf(2) }
    var openedChat by remember { mutableStateOf<String?>(null) }

    val backdrop = rememberLayerBackdrop()

    val titles = listOf(
        "Контакты",
        "Звонки",
        "Чаты",
        "Настройки"
    )

    val symbols = listOf("♙", "☎", "☷", "⚙")

    if (openedChat != null) {
        ChatScreen(
            name = openedChat!!,
            backdrop = backdrop,
            onBack = { openedChat = null }
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .pointerInput(Unit) {
                    var totalDrag = 0f

                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, amount ->
                            totalDrag += amount
                        },
                        onDragEnd = {
                            if (totalDrag < -80f) {
                                selectedTab =
                                    (selectedTab + 1).coerceAtMost(3)
                            } else if (totalDrag > 80f) {
                                selectedTab =
                                    (selectedTab - 1).coerceAtLeast(0)
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
                        title = "Контакт ${index + 1}",
                        subtitle = "был(а) недавно",
                        onClick = {
                            openedChat = "Контакт ${index + 1}"
                        }
                    )
                }

                1 -> repeat(8) { index ->
                    ContactRow(
                        title = "Контакт ${index + 1}",
                        subtitle = "Недавний звонок",
                        onClick = {
                            openedChat = "Контакт ${index + 1}"
                        }
                    )
                }

                2 -> repeat(12) { index ->
                    ContactRow(
                        title = "Собеседник ${index + 1}",
                        subtitle = "Последнее сообщение",
                        onClick = {
                            openedChat = "Собеседник ${index + 1}"
                        }
                    )
                }

                3 -> listOf(
                    "Оформление",
                    "Уведомления",
                    "Конфиденциальность",
                    "Данные и память",
                    "О приложении"
                ).forEach { setting ->
                    ContactRow(
                        title = setting,
                        subtitle = "",
                        onClick = {}
                    )
                }
            }
        }

        LiquidBottomTabs(
            selectedTabIndex = { selectedTab },
            onTabSelected = { selectedTab = it },
            backdrop = backdrop,
            tabsCount = titles.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.dp)
        ) {
            titles.forEachIndexed { index, title ->
                LiquidBottomTab(
                    onClick = { selectedTab = index }
                ) {
                    BasicText(
                        text = symbols[index],
                        style = TextStyle(
                            color = if (selectedTab == index) {
                                Accent
                            } else {
                                SecondaryText
                            },
                            fontSize = 22.sp
                        )
                    )

                    BasicText(
                        text = title,
                        style = TextStyle(
                            color = if (selectedTab == index) {
                                Accent
                            } else {
                                SecondaryText
                            },
                            fontSize = 10.sp,
                            fontWeight = if (selectedTab == index) {
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
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF191919))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF30343A)),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = title.take(1),
                style = TextStyle(
                    color = Color.White,
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

@Composable
private fun ChatScreen(
    name: String,
    backdrop: Backdrop,
    onBack: () -> Unit
) {
    val messages = remember(name) {
        mutableStateListOf(
            ChatMessage("Привет!", false),
            ChatMessage("Привет, как дела?", true),
            ChatMessage("Всё хорошо, а у тебя?", false)
        )
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Фон, который захватывает Backdrop
        Column(
            modifier = Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
        ) {
            // Хедер
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF111111))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = "‹",
                    modifier = Modifier
                        .clickable(onClick = onBack)
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    style = TextStyle(
                        color = Accent,
                        fontSize = 34.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF30343A)),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = name.take(1),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    BasicText(
                        text = name,
                        style = TextStyle(
                            color = MainText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    BasicText(
                        text = "был(а) недавно",
                        style = TextStyle(
                            color = SecondaryText,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            // Сообщения
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .padding(bottom = 90.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                messages.forEach { message ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (message.mine) {
                            Arrangement.End
                        } else {
                            Arrangement.Start
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .widthIn(max = 290.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 18.dp,
                                        topEnd = 18.dp,
                                        bottomStart = if (message.mine) {
                                            18.dp
                                        } else {
                                            5.dp
                                        },
                                        bottomEnd = if (message.mine) {
                                            5.dp
                                        } else {
                                            18.dp
                                        }
                                    )
                                )
                                .background(
                                    if (message.mine) {
                                        Color(0xFF263A50)
                                    } else {
                                        Color(0xFF202020)
                                    }
                                )
                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 10.dp
                                )
                        ) {
                            BasicText(
                                text = message.text,
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Стеклянное поле поверх фона
        LiquidMessageInput(
            backdrop = backdrop,
            onSend = { text ->
                messages.add(ChatMessage(text, true))
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 12.dp)
                .padding(bottom = 8.dp)
        )
    }
}
