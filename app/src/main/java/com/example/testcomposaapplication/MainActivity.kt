package com.example.testcomposaapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcomposaapplication.ui.theme.DesignSystemTheme
import java.util.*

class MainActivity : ComponentActivity() {

    var isOn by mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignSystemTheme(isOn) {
                Surface(color = DesignSystemTheme.colors.backgroundPrimary) {
                    renderApplication()

                    //TODO demo
                    //LazyColumnDemo()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalComposeUiApi
    @Composable
    fun renderApplication() {
        // Compose version of context
        val context = LocalContext.current

        // Your App state (or it may be App State)
        val appState: MutableState<AppState> = remember { mutableStateOf(AppState.Empty) }
        appState.value = AppState.Feature1("Calendar")
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(DesignSystemTheme.colors.backgroundPrimary),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = {
//                appState.value = listOf(
//                    AppState.Empty,
//                    AppState.Feature1("test1"),
//                    AppState.Feature2("test2", R.drawable.ic_launcher_background)
//                ).random()
//            }) {
//                Text(
//                    text = "Change State",
//                    fontSize = 22.sp,
//                    modifier = Modifier
//                        .background(DesignSystemTheme.colors.accentActive)
//                        .padding(10.dp)
//                )
//            }

            when (appState.value) {
                is AppState.Empty -> {
                    Text(
                        text = "Nothing",
                        color = Color.White,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .background(DesignSystemTheme.colors.brandMtsRed)
                            .padding(10.dp)
                    )
                }
                is AppState.Feature1 -> {
                    val feature1 = appState.value as AppState.Feature1
                    SelectableCalendarSample()
                }
                is AppState.Feature2 -> {
                    val feature2 = appState.value as AppState.Feature2
                    Text(
                        text = "Feature2 " + feature2.text,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .background(DesignSystemTheme.colors.accentActive)
                            .padding(10.dp)
                    )
                    Image(
                        painter = painterResource(feature2.drawableRes),
                        contentDescription = ""
                    )
                }
            }
        }
    }


@Composable
fun Greeting() {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Color.Blue else Color.Green

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {},
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Button",
                color = Color.White,
                style = DesignSystemTheme.typography.p2.medium
            )
        }
    }
}

fun initState(): MyCalendarState {
    val calendar = Calendar.getInstance()
    val monthNum = calendar.get(Calendar.MONTH)
    val daysCount = calendar.getActualMaximum(Calendar.DATE)
    return MyCalendarState(
        year = calendar.get(Calendar.YEAR),
        month = monthNum,
        startDate = null,
        endDate = null,
        daysCount = daysCount
    )
}

@Composable
fun LazyColumnDemo() {
    val list = listOf(
        "A", "B", "C", "D"
    ) + ((0..100).map { it.toString() })
    val lazyListState: LazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState, modifier = Modifier.fillMaxHeight()) {
        items(items = list, itemContent = { item ->
            Text(
                text = item,
                style = TextStyle(fontSize = 80.sp),
                modifier = Modifier.clickable {
                    // Sample lazyListState.firstVisibleItemIndex
                }
            )
        })
    }
}