package com.example.daytoo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.daytoo.R
import com.example.daytoo.ui.theme.CookieMonster
import com.example.daytoo.ui.theme.DarkRed
import com.example.daytoo.ui.theme.HoneyBee
import com.example.daytoo.ui.theme.Red
import com.example.daytoo.ui.theme.shadow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AskOut(modifier: Modifier = Modifier, showDecision: (Boolean) -> Unit = {}) {
    var no1 by remember { mutableStateOf(true) }
    var no2 by remember { mutableStateOf(false) }
    var no3 by remember { mutableStateOf(false) }
    var no4 by remember { mutableStateOf(false) }
    var no5 by remember { mutableStateOf(false) }
    var drawableId by remember {
        mutableStateOf(R.drawable.happy_boy)
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Red)
    ) {
        val (happyImage, sadImage, askOutText, btns, noBtn2, noBtn3, noBtn4, noBtn5) = createRefs()

            GifImage(iconId = drawableId, modifier = Modifier
            .size(200.dp)
            .constrainAs(happyImage) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0f, topMargin = 30.dp)
            })

        Text(
            text = "Would you like to go on \nour next date, \ndarling?",
            modifier = Modifier.constrainAs(askOutText) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(
                    top = happyImage.bottom,
                    bottom = parent.bottom,
                    bias = 0f,
                    topMargin = 20.dp
                )
            },
            fontFamily = CookieMonster.regular,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .constrainAs(btns) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(
                        top = askOutText.bottom,
                        bottom = parent.bottom,
                        bias = 0f,
                        topMargin = 60.dp
                    )
                }
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ClickableText(
                text = AnnotatedString("YESS \uD83D\uDE18"),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = HoneyBee.regular,
                    textAlign = TextAlign.Center
                ),
                onClick = {
                    drawableId = R.drawable.happy_boy
                    showDecision(true)
                },
                modifier = Modifier
                    .shadow(
                        color = DarkRed,
                        offsetX = 20.dp,
                        offsetY = 20.dp,
                        spread = 2.dp,
                        borderRadius = 20.dp,
                        blurRadius = 10.dp,
                    )
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                    )
                    .padding(4.dp),
            )

            if (no1) ClickableText(
                text = AnnotatedString("NO \uD83E\uDD79"),
                modifier = Modifier
                    .shadow(
                        color = DarkRed,
                        offsetX = 20.dp,
                        offsetY = 20.dp,
                        spread = 2.dp,
                        borderRadius = 20.dp,
                        blurRadius = 10.dp,
                    )
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                    )
                    .padding(4.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = HoneyBee.regular,
                    textAlign = TextAlign.Center
                ),
                onHover = {},
                onClick = {
                    drawableId = R.drawable.sad
                    no1 = false
                    no2 = true
                    no3 = false
                    no4 = false
                    no5 = false
                }
            )
        }

        if (no2) ClickableText(
            text = AnnotatedString("NO NO NO \uD83E\uDD7A"),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            ),
            onHover = {},
            onClick = {
                no1 = false
                no2 = false
                no3 = true
                no4 = false
                no5 = false
            },
            modifier = Modifier
                .constrainAs(noBtn2) {
                    linkTo(start = parent.start, end = parent.end, bias = 1f, endMargin = 30.dp)
                    linkTo(
                        top = askOutText.bottom,
                        bottom = parent.bottom,
                        topMargin = 80.dp
                    )
                }
                .shadow(
                    color = DarkRed,
                    offsetX = 20.dp,
                    offsetY = 20.dp,
                    spread = 2.dp,
                    borderRadius = 20.dp,
                    blurRadius = 10.dp,
                )
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                )
                .padding(4.dp)
        )

        if (no3) ClickableText(
            text = AnnotatedString("NAHIII!! \uD83E\uDD72"),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            ),
            onHover = {},
            onClick = {
                drawableId = R.drawable.sadder
                no1 = false
                no2 = false
                no3 = false
                no4 = true
                no5 = false
            },
            modifier = Modifier
                .constrainAs(noBtn3) {
                    linkTo(start = parent.start, end = parent.end, endMargin = 20.dp)
                    linkTo(
                        top = askOutText.bottom,
                        bottom = parent.bottom,
                        bias = 1f,
                        bottomMargin = 60.dp
                    )
                }
                .shadow(
                    color = DarkRed,
                    offsetX = 20.dp,
                    offsetY = 20.dp,
                    spread = 2.dp,
                    borderRadius = 20.dp,
                    blurRadius = 10.dp,
                )
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                )
                .padding(4.dp)
        )

        if (no4) ClickableText(
            text = AnnotatedString("SAHI ME NAA HAI? \uD83D\uDE2D"),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            ),
            onHover = {},
            onClick = {
                drawableId = R.drawable.saddest
                no1 = false
                no2 = false
                no3 = false
                no4 = false
                no5 = true
            },
            modifier = Modifier
                .constrainAs(noBtn4) {
                    linkTo(start = parent.start, end = parent.end, bias = 0f, startMargin = 30.dp)
                    linkTo(
                        top = askOutText.bottom,
                        bottom = parent.bottom,
                        bias = 0f,
                        topMargin = 150.dp
                    )
                }
                .shadow(
                    color = DarkRed,
                    offsetX = 20.dp,
                    offsetY = 20.dp,
                    spread = 2.dp,
                    borderRadius = 20.dp,
                    blurRadius = 10.dp,
                )
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                )
                .padding(4.dp)
        )

        if (no5) ClickableText(
            text = AnnotatedString("PAKKI BAAT HAI!? \uD83D\uDC94"),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            ),
            onHover = {},
            onClick = {
                showDecision(false)
            },
            modifier = Modifier
                .constrainAs(noBtn5) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(
                        top = askOutText.bottom,
                        bottom = parent.bottom,
                    )
                }
                .shadow(
                    color = DarkRed,
                    offsetX = 20.dp,
                    offsetY = 20.dp,
                    spread = 2.dp,
                    borderRadius = 20.dp,
                    blurRadius = 10.dp,
                )
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                )
                .padding(4.dp)
        )
    }
}

@Composable
@Preview
fun PreviewAskOut() {
    AskOut()
}