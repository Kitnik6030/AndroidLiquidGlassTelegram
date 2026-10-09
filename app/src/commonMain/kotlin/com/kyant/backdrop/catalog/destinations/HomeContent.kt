
package com.kyant.backdrop.catalog.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    Chat("Избранное", "Заметки и сообщения", "12:40", "★", Color(0xFF1687F8)),
    Chat("Александр", "Ты уже посмотрел?", "12:32", "А", Color(0xFF9274D8), 2),
    Chat("Друзья", "Максим: всем привет", "11:58", "Д", Color(0xFF4DAA87), 5),
    Chat("Разработка", "Новый коммит готов", "10:41", "К", Color(0xFFE69B45)),
    Chat("Мама", "Не забудь написать", "Вчера", "М", Color(0xFFE47791)),
    Chat("Новости", "Последние обновления", "Вчера", "Н", Color(0xFF558DD9)),
    Chat("Игры", "Кто сегодня играет?", "Вт", "И", Color(0xFF7F9C58))
)

@Composable
fun HomeContent(onNavigate: (com.kyant.backdrop.catalog.CatalogDestination) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                .padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText("⌕", style = TextStyle(color = Secondary, fontSize = 22.sp))
            Spacer(Modifier.width(8.dp))
            BasicText("Поиск", style = TextStyle(color = Secondary, fontSize = 16.sp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicText(
                "Все чаты",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(Modifier.weight(1f))
            BasicText("Изменить", style = TextStyle(color = Blue, fontSize = 15.sp))
        }

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
                            .padding(start = 82.dp)
                            .height(0.5.dp)
                            .background(Color(0xFFE5E5EA))
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomTab("☷", "Контакты")
            BottomTab("◷", "Звонки")
            BottomTab("●", "Чаты", selected = true)
            BottomTab("⚙", "Настройки")
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
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
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
                        .defaultMinSize(minWidth = 21.dp, minHeight = 21.dp)
                        .clip(CircleShape)
                        .background(Blue)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        chat.unread.toString(),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomTab(
    symbol: String,
    label: String,
    selected: Boolean = false
) {
    Column(
        modifier = Modifier
            .clickable { }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            symbol,
            style = TextStyle(
                color = if (selected) Blue else Secondary,
                fontSize = 22.sp
            )
        )
        Spacer(Modifier.height(3.dp))
        BasicText(
            label,
            style = TextStyle(
                color = if (selected) Blue else Secondary,
                fontSize = 11.sp
            )
        )
    }
}
