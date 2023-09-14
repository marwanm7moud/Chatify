package com.awesome.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.awesome.ui.R
import com.awesome.ui.ui.theme.ChatifyTheme
import com.awesome.viewmodel.home.ChatUiState

@Composable
fun Chat(
    chat: ChatUiState = ChatUiState()
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
    ) {
        val (image, unreadedMessage, date, name, lastMessage) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(chat.Image)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(name.start)
                }
        )

        Text(
            text = chat.name,
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(image.end)
                    bottom.linkTo(lastMessage.top)
                }
        )
        Text(
            text = chat.lastMessage,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .constrainAs(lastMessage) {
                    start.linkTo(image.end)
                    top.linkTo(name.bottom)
                }
        )

        Text(
            text = chat.lastMessageDateSent.toString(),
            modifier = Modifier
                .constrainAs(date) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        if (chat.unreadMessageCount != 0)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFED8936),
                modifier = Modifier
                    .size(18.dp)
                    .constrainAs(unreadedMessage) {
                        top.linkTo(date.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                    text = chat.unreadMessageCount.toString(),
                    color = Color.White,
                )
            }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ChatPreview() {
    ChatifyTheme() {
        Chat()
    }
}