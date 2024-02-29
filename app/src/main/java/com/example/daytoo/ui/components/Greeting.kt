package com.example.daytoo.ui.components

import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.daytoo.R
import com.example.daytoo.ui.theme.CookieMonster
import com.example.daytoo.ui.theme.Grey
import com.example.daytoo.ui.theme.HoneyBee
import kotlinx.coroutines.delay

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,
             showAskOut: () -> Unit = {}, btnClick: () -> Unit) {
    val compositionParrot by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.parrot_lottie)
    )
    val compositionRose by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.rose_lottie)
    )
    val progressParrot by animateLottieCompositionAsState(
        compositionParrot,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false
    )
    val progressRose by animateLottieCompositionAsState(
        compositionRose,
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
        val (parrotLottie, helloTxt, roseLottie, delayedText, btn) = createRefs()

        Text(
            text = "Aaj idhar tap croww!",
            modifier = Modifier.constrainAs(btn) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0f, topMargin = 30.dp)
            }.clickable { btnClick() },
            fontFamily = CookieMonster.regular,
            fontSize = 30.sp
        )

        LottieAnimation(
            compositionParrot,
            progressParrot,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(parrotLottie) {
                    linkTo(start = parent.start, end = parent.end, bias = 0f)
                    linkTo(top = parent.top, bottom = helloTxt.top, bias = 1f, bottomMargin = 10.dp)
                }
        )

        Text(
            text = "Hello $name!",
            modifier = Modifier.constrainAs(helloTxt) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = parent.bottom)
            },
            fontFamily = CookieMonster.regular,
            fontSize = 42.sp
        )

        DelayedTextComponent(modifier = Modifier.constrainAs(delayedText) {
            linkTo(start = parent.start, end = parent.end, bias = 0f, startMargin = 15.dp)
            linkTo(top = parent.top, bottom = parent.bottom, bias = 1f, bottomMargin = 15.dp)
        })

        LottieAnimation(
            compositionRose,
            progressRose,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(roseLottie) {
                    linkTo(start = parent.start, end = parent.end, bias = 1f)
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 1f)
                }
                .clickable {
                    showAskOut()
                }
        )
    }
}

@Composable
fun DelayedTextComponent(
    modifier: Modifier = Modifier
) {
    var showFirstMessage by remember { mutableStateOf(false) }
    var showSecondMessage by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        delay(7000)
        showFirstMessage = true
        delay(5000)
        showFirstMessage = false
        showSecondMessage = true
    }

    Column(modifier) {
        if (showFirstMessage) {
            Text(
                "Samjh nahi aa raha kya ki\n kya karna h ab?",
                fontSize = 24.sp,
                fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            )
        }
        if (showSecondMessage) {
            Text(
                "Rose pe tap crowww!!!", fontSize = 24.sp, fontFamily = HoneyBee.regular,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = iconId).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )
}