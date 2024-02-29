package com.example.daytoo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.daytoo.R
import com.example.daytoo.ui.theme.CookieMonster
import com.example.daytoo.ui.theme.Grey
import com.example.daytoo.ui.theme.HoneyBee

@Composable
fun Decision(
    type: Boolean,
    modifier: Modifier = Modifier
) {
    if (type) {
        val compositionHurray by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.hurray_lottie)
        )
        val progressHurray by animateLottieCompositionAsState(
            compositionHurray,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        val compositionHappy by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.happy_emoji_lottie)
        )
        val progressHappy by animateLottieCompositionAsState(
            compositionHappy,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        val compositionKiss by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.kiss_lottie)
        )
        val progressKiss by animateLottieCompositionAsState(
            compositionKiss,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .background(Grey),
        ) {
            val (happyTxt, hurrayLottie, happyLottie, afterTxt, kissLottie) = createRefs()


            LottieAnimation(
                compositionHappy,
                progressHappy,
                modifier = Modifier
                    .size(200.dp)
                    .constrainAs(happyLottie) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            bias = 0f,
                            topMargin = 15.dp
                        )
                    }
            )


            LottieAnimation(
                compositionHurray,
                progressHurray,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(hurrayLottie) {
                        linkTo(start = parent.start, end = parent.end, bias = 1f)
                        linkTo(top = parent.top, bottom = parent.bottom, bias = 1f)
                    }
            )

            Text(
                text = "Wuhhuuuu!",
                modifier = Modifier.constrainAs(happyTxt) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.25f)
                },
                fontFamily = CookieMonster.regular,
                fontSize = 42.sp
            )

            Text(
                text = "Chlo ab jaldi jaldi se \nplan bana le pyara sa! \n\nOr whatsapp pe mujhko ek pyara sa text karde... \n\nOr iskeee liye me next date pe ek pyari si HUG \uD83E\uDEC2 or KISS \uD83D\uDC8B toh deserve karta hi hoon!! \n\n\n\uD83D\uDC69\uD83C\uDFFB\u200D❤️\u200D\uD83D\uDC8B\u200D\uD83D\uDC68\uD83C\uDFFC",
                modifier = Modifier
                    .constrainAs(afterTxt) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = happyTxt.bottom, bottom = parent.bottom, bias = 0.35f)
                    }
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp),
                fontFamily = HoneyBee.regular,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )



            LottieAnimation(
                compositionKiss,
                progressKiss,
                modifier = Modifier
                    .size(200.dp)
                    .constrainAs(kissLottie) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            bias = 1f,
                            bottomMargin = 20.dp
                        )
                    }
            )
        }
    } else {
        val compositionCryFace by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.face_cry_lottie)
        )
        val progressCryFace by animateLottieCompositionAsState(
            compositionCryFace,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        val compositionSad by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.cry_emoji_lottie)
        )
        val progressSad by animateLottieCompositionAsState(
            compositionSad,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        val compositionHeartBroke by rememberLottieComposition(
            LottieCompositionSpec
                .RawRes(R.raw.heart_broke_lottie)
        )
        val progressHeartBroke by animateLottieCompositionAsState(
            compositionHeartBroke,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )
        ConstraintLayout(
            modifier = modifier
                .fillMaxSize()
                .background(Grey),
        ) {
            val (sadTxt, cryFaceLottie, sadLottie, afterTxt, heartBrokeLottie) = createRefs()


            LottieAnimation(
                compositionSad,
                progressSad,
                modifier = Modifier
                    .size(200.dp)
                    .constrainAs(sadLottie) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            bias = 0f,
                            topMargin = 15.dp
                        )
                    }
            )


            LottieAnimation(
                compositionCryFace,
                progressCryFace,
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(cryFaceLottie) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = sadTxt.bottom, bottom = afterTxt.top)
                    }
            )

            Text(
                text = "Fuck!",
                modifier = Modifier.constrainAs(sadTxt) {
                    linkTo(start = parent.start, end = parent.end)
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.25f)
                },
                fontFamily = CookieMonster.regular,
                fontSize = 42.sp
            )

            Text(
                text = "Sapna toota toh\nYeh Dil Kabhi Jalta Hai\nHaaa Thora Dard Hua\nPar Chalta Hai!!!\n\n\uD83D\uDC94\uD83D\uDC94\n\n\nI Repect Your Decision\uD83D\uDC4F\uD83C\uDFFB",
                modifier = Modifier
                    .constrainAs(afterTxt) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = sadTxt.bottom, bottom = parent.bottom, bias = 0.35f)
                    }
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp),
                fontFamily = HoneyBee.regular,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )



            LottieAnimation(
                compositionHeartBroke,
                progressHeartBroke,
                modifier = Modifier
                    .size(200.dp)
                    .constrainAs(heartBrokeLottie) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            bias = 1f,
                            bottomMargin = 20.dp
                        )
                    }
            )
        }
    }
}

@Composable
@Preview
fun PreviewDecision() {
    Decision(type = false)
}

@Composable
@Preview
fun PreviewDecision2() {
    Decision(type = true)
}