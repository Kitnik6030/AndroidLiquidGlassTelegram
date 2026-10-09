
package com.kyant.backdrop.catalog.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.catalog.BackdropDemoScaffold
import com.kyant.backdrop.catalog.CatalogDestination
import com.kyant.backdrop.catalog.components.LiquidBottomTab
import com.kyant.backdrop.catalog.components.LiquidBottomTabs

private val Background = Color(0xFFF5F5F9)
private val Blue = Color(0xFF1687F8)
private val Secondary = Color(0xFF8E8E93)

private data class Chat(
    val name: String,
    val message: String,
    val time: String,
    val avatar: String,
    val color: Color,
    val unread: Int = 0
)

private val demoChats = listOf(
    Chat("Избранное", "Заметки и сообщения", "12:40", "★", Blue),
    Chat("Александр", "Ты уже посмотрел?", "12:32", "А", Color(0xFF9274D8), 2),
    Chat("Друзья", "Максим: всем привет", "11:58", "Д", Color(0xFF4DAA87), 5),
    Chat("Разработка", "Новый коммит готов", "10:41", "К", Color(0xFFE69B45)),
    Chat("Мама", "Не забудь написать", "Вчера", "М", Color(0xFFE47791)),
    Chat("Новости", "Последние обновления", "Вчера", "Н", Color(0xFF558DD9)),
    Chat("Игры", "Кто сегодня играет?", "Вт", "И", Color(0xFF7F9C58))
)

@Composable
fun HomeContent(onNavigate: (CatalogDestination) -> Unit) {
    var selectedTab by rememberSaveable { mutableIntStateOf(2) }

    BackdropDemoScaffold { backdrop ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .systemBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .layerBackdrop(backdrop)
                    .padding(bottom = 88.dp)
            ) {
                when (selectedTab) {
                    0 -> SimpleSection(
                        "Контакты",
                        listOf("Александр", "Максим", "Мама", "Друзья")
                    )

                    1 -> SimpleSection(
                        "Звонки",
                        listOf("Александр · исходящий", "Максим · пропущенный")
                    )

                    2 -> ChatsScreen()

                    3 -> SimpleSection(
                        "Настройки",
                        listOf(
                            "Мой профиль",
                            "Уведомления",
                            "Конфиденциальность",
                            "Оформление"
                        )
                    )
                }
            }

            LiquidBottomTabs(
                selectedTabIndex = { selectedTab },
                onTabSelected = { selectedTab = it },
                backdrop = backdrop,
                tabsCount = 4,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                LiquidBottomTab(
                    onClick = { selectedTab = 0 }
                ) {
                    BasicText("♙", style = tabIconStyle(selectedTab == 0))
                    BasicText("Контакты", style = tabLabelStyle(selectedTab == 0))
                }

                LiquidBottomTab(
                    onClick = { selectedTab = 1 }
                ) {
                    BasicText("◷", style = tabIconStyle(selectedTab == 1))
                    BasicText("Звонки", style = tabLabelStyle(selectedTab == 1))
                }

                LiquidBottomTab(
                    onClick = { selectedTab = 2 }
                ) {
                    BasicText("●", style = tabIconStyle(selectedTab == 2))
                    BasicText("Чаты", style = tabLabelStyle(selectedTab == 2))
                }

                LiquidBottomTab(
                    onClick = { selectedTab = 3 }
                ) {
                    BasicText("⚙", style = tabIconStyle(selectedTab == 3))
                    BasicText("Настройки", style = tabLabelStyle(selectedTab == 3))
                }
            }
        }
    }
}

private fun tabIconStyle(selected: Boolean) = TextStyle(
    color = if (selected) Blue else Secondary,
    fontSize = 22.sp
)

private fun tabLabelStyle(selected: Boolean) = TextStyle(
    color = if (selected) Blue else Secondary,
    fontSize = 11.sp
)

@Composable
private fun ChatsScreen() {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                "Изменить",
                style = TextStyle(color = Blue, fontSize = 16.sp)
            )

            BasicText(
                "Telegram",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            BasicText(
                "＋",
                style = TextStyle(color = Blue, fontSize = 28.sp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFE7E7ED))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText("⌕", style = TextStyle(color = Secondary, fontSize = 22.sp))
            Spacer(Modifier.width(8.dp))
            BasicText("Поиск", style = TextStyle(color = Secondary, fontSize = 16.sp))
        }

        BasicText(
            "Все чаты",
            Modifier.padding(start = 20.dp, top = 18.dp, bottom = 12.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            demoChats.forEachIndexed { index, chat ->
                ChatRow(chat)

                if (index != demoChats.lastIndex) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 78.dp)
                            .height(0.5.dp)
                            .background(Color(0xFFE5E5EA))
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatRow(chat: Chat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(chat.color),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                chat.avatar,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            BasicText(
                chat.name,
                style = TextStyle(
                    color = Color(0xFF17171A),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(5.dp))

            BasicText(
                chat.message,
                style = TextStyle(color = Secondary, fontSize = 14.sp)
            )
        }

        Spacer(Modifier.width(6.dp))

        Column(horizontalAlignment = Alignment.End) {
            BasicText(
                chat.time,
                style = TextStyle(color = Secondary, fontSize = 12.sp)
            )

            if (chat.unread > 0) {
                Spacer(Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Blue)
                        .padding(horizontal = 7.dp, vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        chat.unread.toString(),
                        style = TextStyle(color = Color.White, fontSize = 12.sp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SimpleSection(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        BasicText(
            title,
            Modifier.padding(start = 4.dp, top = 20.dp, bottom = 18.dp),
            style = TextStyle(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
        ) {
            items.forEachIndexed { index, item ->
                BasicText(
                    item,
                    Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(18.dp),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                if (index != items.lastIndex) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp)
                            .height(0.5.dp)
                            .background(Color(0xFFE5E5EA))
                    )
                }
            }
        }
    }
}
