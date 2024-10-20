package com.example.daytoo.ui

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.daytoo.R
import com.example.daytoo.models.GalleryData
import com.example.daytoo.ui.theme.CookieMonster
import com.example.daytoo.ui.theme.DarkRed
import com.example.daytoo.ui.theme.DayTooTheme
import com.example.daytoo.ui.theme.Grey
import com.example.daytoo.ui.theme.HoneyBee
import com.example.daytoo.ui.theme.shadow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

class GalleryActivity : ComponentActivity() {

    private lateinit var database: FirebaseFirestore
    private val galleryData: MutableMap<String, MutableList<GalleryData>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseFirestore.getInstance()
        readData()
        setContent {
            DayTooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showFirstMessage by remember { mutableStateOf(false) }

                    LaunchedEffect(true) {
                        delay(4000)
                        showFirstMessage = true
                    }
                    if (showFirstMessage) ShowGallery()
                    else Box(
                        Modifier
                            .fillMaxSize()
                            .padding(all = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Thora sabr karo babe!",
                                fontFamily = CookieMonster.regular,
                                fontSize = 40.sp
                            )

                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .size(50.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShowGallery() {
        var showFirstMessage by remember { mutableStateOf(true) }
        if (showFirstMessage) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Grey)
                    .padding(all = 16.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp
                ),
                contentPadding = PaddingValues(10.dp),
            ) {
                Log.d("ShowGallery", "$galleryData ")
                galleryData.keys.forEach { key ->
                    val resources: Triple<Int, Brush, Color> = when (key) {
                        "snaps" -> Triple(
                            R.drawable.ic_snap, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFFB91372),
                                    Color(0XFF6B0F1A)
                                )
                            ), Color.White
                        )

                        "auto" -> Triple(
                            R.drawable.ic_auto, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFF864BA2),
                                    Color(0XFFBF3A30)
                                )
                            ), Color.White
                        )

                        "meets" -> Triple(
                            R.drawable.ic_meet, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFFE01C34),
                                    Color(0XFFACABB0)
                                )
                            ), Color.White
                        )

                        "cafe" -> Triple(
                            R.drawable.ic_cafe, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFFCB218E),
                                    Color(0XFF6617CB)
                                )
                            ), Color.White
                        )

                        "park" -> Triple(
                            R.drawable.ic_park, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFFFE0944),
                                    Color(0XFFFEAE96)
                                )
                            ), Color.White
                        )

                        else -> Triple(
                            R.drawable.ic_park, Brush.verticalGradient(
                                colors = listOf(
                                    Color(0XFFFE0944),
                                    Color(0XFFFEAE96)
                                )
                            ), Color.Black
                        )
                    }
                    item {
                        var showDialog by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.4f)
                                .height(150.dp)
                                .clip(RoundedCornerShape(10))
                                .background(brush = resources.second, RoundedCornerShape(10))
                                .clickable {
                                    showDialog = true
                                }
                        ) {
                            Text(
                                text = key,
                                fontSize = 30.sp,
                                fontFamily = CookieMonster.regular,
                                color = resources.third,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            Icon(
                                painter = painterResource(id = resources.first),
                                modifier = Modifier
                                    .size(65.dp)
                                    .align(Alignment.BottomEnd),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            if (showDialog) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Dialog(
                                        onDismissRequest = { showDialog = false },
                                        properties = DialogProperties(usePlatformDefaultWidth = false)
                                    ) {
                                        ShowGalleryItem(galleryData.getValue(key))
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "*Darasal abhi kaam chal raha hai... \nPar bohot time lagg gya... \ntoh isiliye abhi hi de raha hoon... SORRY\uD83E\uDD79\uD83E\uDD79\n\n Yaha bhi tap karo vese ek baar",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.clickable { showFirstMessage = false }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(Grey)
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Darasal I want to ask you out for a date! \nUmmm next week 25,26,27 ko moksha hai... toh kyaaa tu aaegi kisi ek din bhi?\n\nIk ik ye baat me text pe bhi puch sakta thaaaaa... but mujhe ye cheez thore ache tareeke se puchni thi... or call apni aaj kal ho nahi rahi... or me thora excited hu or chahta bhi hu ki tu aae... toh isiliye thora special karne ke liye hui hui hui... btw gallery vala plan phele se thaaaa... bss timing ye choose karli... I GUESS it's been a long time ki ese dhang se baat cheet nahi hui...\n\nIk tu blr me hogi... but still it's just I wanted to ask you out, so that's why\n\n\nAnd please otherwise matt lena kuch... \n\n\nAnd just a disclaimer all this is irrespective of your decision yeh toh bss tujhe hepi hepi feel karane ke liye tha... ye me phele se socah hua thaaa... Hope tujhe acha laga ho\nHehe bbye",
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()){
                    ClickableText(
                        text = AnnotatedString("YESS"),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HoneyBee.regular,
                            textAlign = TextAlign.Center
                        ),
                        onClick = {
                            Toast.makeText(this@GalleryActivity, "wuhuuuu", Toast.LENGTH_SHORT).show()
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
                    Spacer(Modifier.width(10.dp))
                    ClickableText(
                        text = AnnotatedString("NO"),
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
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = HoneyBee.regular,
                            textAlign = TextAlign.Center
                        ),
                        onHover = {},
                        onClick = {
                            Toast.makeText(this@GalleryActivity, "Okayiii", Toast.LENGTH_SHORT).show()
                            val pref = applicationContext.getSharedPreferences(
                                "MyPref",
                                0
                            ) // 0 - for private mode
                            pref.edit().putBoolean("Open_App", false).apply()
                        }
                    )
                }
            }
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class) @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ShowGalleryItem(galleryData: List<GalleryData>) {
        val pagerState = rememberPagerState() { galleryData.size }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { index ->
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300), label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(10)
                    )
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
                    .padding(vertical = 12.dp, horizontal = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("ShowGalleryItem", "ShowGalleryItem: ${galleryData[index].type }")
                    if(galleryData[index].type == "image") {
                        Log.d("ShowGalleryItem", "ShowGalleryItem: 1")
                        AsyncImage(
                            model = galleryData[index].url, contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(500.dp)
                                .clip(RoundedCornerShape(5))
                        )
                    }
                    else {
                        val context = LocalContext.current
                        val exoPlayer = remember {
                            ExoPlayer.Builder(context).build().apply {
                                setMediaItem(MediaItem.fromUri(galleryData[index].url))
                                repeatMode = ExoPlayer.REPEAT_MODE_ALL
                                playWhenReady = true
                                volume = 1f
                                prepare()
                                play()
                            }
                        }
                        DisposableEffect(Unit) {
                            onDispose {
                                exoPlayer.release()
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(500.dp)
                                .clip(RoundedCornerShape(5))){
                            AndroidView(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(5)),
                                factory = {
                                    PlayerView(context).apply {
                                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                                        setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
                                        setKeepContentOnPlayerReset(true)
                                        player = exoPlayer
                                        useController = false
                                        FrameLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                        )
                                    }
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = galleryData[index].title,
                        fontWeight = FontWeight.W500,
                        fontFamily = CookieMonster.regular,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .background(
                                color = Color(0x99FFFFFF),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }

    private fun readData() {
        val dataDocumentRef = database.collection("memories").document("data")

        Log.d("show data", "readData:")
        // Retrieve all collections under the "data" document
        dataDocumentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val dataDocument = documentSnapshot.data
                    val collections = setOf("auto", "park", "miscs", "meets", "cafe")
                    for (collectionName in collections) {
                        // Access each collection
                        val collectionRef = dataDocumentRef.collection(collectionName)
                        // Retrieve all documents within each collection
                        collectionRef.get()
                            .addOnSuccessListener { collectionSnapshot ->
                                for (document in collectionSnapshot.documents) {
                                    val data = document.data
                                    val fetchedData = GalleryData(
                                        title = (data?.get("title") as String),
                                        type = (data["type"] as String),
                                        url = (data["url"] as String)
                                    )
                                    val cn =
                                        if (collectionName == "miscs") "snaps" else collectionName
                                    if (galleryData[cn] == null)
                                        galleryData[cn] = mutableListOf(fetchedData)
                                    else galleryData[cn]?.add(fetchedData)
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "kuch dikkat hogyi yrr!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }

                } else {
                    Toast.makeText(this, "kuch dikkat hogyi yrr!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "kuch dikkat hogyi yrr!", Toast.LENGTH_SHORT).show()
            }
    }
}
