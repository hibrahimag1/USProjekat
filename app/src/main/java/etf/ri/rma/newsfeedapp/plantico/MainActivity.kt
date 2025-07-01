package etf.ri.rma.newsfeedapp.plantico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import etf.ri.rma.newsfeedapp.plantico.notification.NotificationHelper
import etf.ri.rma.newsfeedapp.plantico.screen.LoadingScreen
import etf.ri.rma.newsfeedapp.plantico.screen.PlantStatusScreen
import etf.ri.rma.newsfeedapp.plantico.ui.theme.PlanticoTheme
import etf.ri.rma.newsfeedapp.plantico.viewModel.SensorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationHelper.createNotificationChannel(this)
        setContent {
            PlanticoTheme {
                SensorScreen()
            }
        }
    }
}

/*
@Composable
fun SensorScreen(vm: SensorViewModel = viewModel()) {
    val light by vm.light.collectAsState()
    val humidity by vm.humidity.collectAsState()



    val context = LocalContext.current
    LaunchedEffect(light, humidity) {
        vm.checkAndNotify(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        light?.let { lx ->
            val progress = (lx / 100f).coerceIn(0f, 1f)
            Text(text = String.format("%.1f lx", lx))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = when {
                    lx < 10f -> "Veoma mračno"
                    lx < 50f -> "Slabo svjetlo"
                    lx < 100f -> "Dobro svjetlo"
                    else -> "Odlično svjetlo"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        } ?: run {
            Text("Učitavanje svjetla…")
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Vlažnost
        Text(text = "Vlažnost tla", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        if (humidity != null) {
            val humidityProgress = (humidity!! / 100f).coerceIn(0f, 1f)
            Text(text = String.format("%.1f %%", humidity))
            LinearProgressIndicator(
                progress = humidityProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        } else {
            Text("Učitavanje vlažnosti…")
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SensorScreenPreview() {
    PlanticoTheme {
        SensorScreen()
    }
}*/

/*
@Composable
fun SensorScreen(vm: SensorViewModel = viewModel()) {
    val light by vm.light.collectAsState()
    val humidity by vm.humidity.collectAsState()



    val context = LocalContext.current
    LaunchedEffect(light, humidity) {
        vm.checkAndNotify(context)
    }
    PlantStatusScreen(
        waterLevel = 100.00.toFloat(),
        lightLevel = 60.toFloat()
    )
}*/
@Composable
fun SensorScreen(vm: SensorViewModel = viewModel()) {
    val light by vm.light.collectAsState()
    val humidity by vm.humidity.collectAsState()
    val context = LocalContext.current

    // Pozivamo notifikacije samo kada su obje vrijednosti != null
    LaunchedEffect(light, humidity) {
        if (light != null && humidity != null) {
            vm.checkAndNotify(context)
        }
    }

    if (light != null && humidity != null) {
        // Prikaz statusa biljke sa realnim vrijednostima
        PlantStatusScreen(
            waterLevel = humidity,
            lightLevel = light
        )
    } else {
        // Prikaz "Učitavanje" dok podaci ne stignu
        LoadingScreen()
    }
}

