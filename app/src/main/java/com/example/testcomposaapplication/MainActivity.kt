package com.example.testcomposaapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.testcomposaapplication.data.Plan
import com.example.testcomposaapplication.day.DefaultDay
import com.example.testcomposaapplication.ui.theme.DesignSystemTheme

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
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalComposeUiApi
    @Composable
    fun renderApplication() {
        // Compose version of context
        val appState: MutableState<AppState> =
            remember { mutableStateOf(AppState.Feature1("Calendar")) }

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
                Column(
                    Modifier.verticalScroll(rememberScrollState())
                ) {
                    SelectableCalendar(dayContent = {
                        DefaultDay(it) {
                            appState.value = AppState.Feature2(it.toString())
                        }
                    })
                }
            }
            is AppState.Feature2 -> {
                val feature2 = appState.value as AppState.Feature2
                ConstraintLayout {
                    val (text, PlansList, Button) = createRefs()
                    Text(
                        feature2.text, fontSize = 25.sp, modifier = Modifier
                            .background(DesignSystemTheme.colors.brandMtsRed)
                            .padding(10.dp)
                            .constrainAs(text) {

                            }
                    )
                    val plans = listOf(
                        Plan("Покормить кота", "Дать дримисов"),
                        Plan("Поесть самому", "Без дримисов")
                    )
                    PlansList(plans = plans,
                        Modifier
                            .padding(10.dp)
                            .constrainAs(PlansList) {
                                top.linkTo(text.bottom, margin = 16.dp)
                            })

                    Button(
                        modifier = Modifier
                            .constrainAs(Button) {
                                top.linkTo(PlansList.bottom, margin = 16.dp)
                            }
                            .background(Color.White)
                            .padding(10.dp),
                        onClick = {
                            appState.value = AppState.Feature1(
                                "test1"
                            )
                        },

                        ) {
                        Text("Назад", fontSize = 25.sp, color = Color.Black)
                    }
                }
            }
        }
    }

    @Composable
    fun PlansList(plans: List<Plan>, mad: Modifier) {
        LazyColumn(modifier = mad) {
            items(plans) { message ->
                Text(
                    text = message.title,
                    color = Color.Black,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .background(DesignSystemTheme.colors.brandMtsRed)
                        .padding(10.dp)
                )

                Text(
                    text = message.desc,
                    color = Color.Black,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .background(DesignSystemTheme.colors.brandMtsRed)
                        .padding(10.dp)
                )
            }
        }
    }
}
