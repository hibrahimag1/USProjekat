package etf.ri.rma.newsfeedapp.plantico.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import etf.ri.rma.newsfeedapp.plantico.R
import etf.ri.rma.newsfeedapp.plantico.viewModel.SensorViewModel

/*
@Composable
fun PlantScreen(
    light: Float?,
    humidity: Float?,
    onRefresh: () -> Unit = {}
) {
    // Odredi je li biljka sretna
    val isHappy = remember(light, humidity) {
        if (light == null || humidity == null) false
        else light >= SensorViewModel.LIGHT_THRESHOLD
                && humidity >= SensorViewModel.HUMIDITY_THRESHOLD
    }

    // Pomoćna tekst poruka
    val statusText = when {
        light == null || humidity == null -> "Učitavanje stanja biljke..."
        !isHappy                         -> "Oh ne, biljka je tužna! 🌱☹️"
        else                             -> "Biljka je sretna! 🌱😊"
    }

    // Koju sliku pokazati
    val imageRes = if (isHappy) R.drawable.plant_happy else R.drawable.plant_sad

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Plantico") },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Slika biljke
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = if (isHappy) "Sretna biljka" else "Tužna biljka",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )

            Spacer(Modifier.height(24.dp))

            // Status tekst
            Text(
                text = statusText,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            // Prikaz indikatora (opcionalno)
            light?.let {
                Text("Svjetlo: ${String.format("%.0f", it)} lx")
                LinearProgressIndicator(
                    progress = (it / 1000f).coerceIn(0f,1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .padding(vertical = 4.dp)
                )
            }

            humidity?.let {
                Text("Vlaga: ${String.format("%.0f", it)}%")
                LinearProgressIndicator(
                    progress = (it / 100f).coerceIn(0f,1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
*/
/*
import com.airbnb.lottie.compose.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.rawResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun PlantScreen(
    light: Float?,
    humidity: Float?,
    onWater: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    // Pragovi
    val lightOk = (light ?: 0f) >= SensorViewModel.LIGHT_THRESHOLD
    val humidityOk = (humidity ?: 0f) >= SensorViewModel.HUMIDITY_THRESHOLD
    val isHappy = lightOk && humidityOk

    // Odabir raw resursa za Lottie
    val animationRes = if (isHappy) R.raw.plant_happy else R.raw.plant_sad

    // Učitavanje animacije
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    // Health score (0–100)
    val healthScore = remember(light, humidity) {
        val lightScore = ((light ?: 0f) / 2000f * 50).coerceIn(0f,50f)
        val humidityScore = ((humidity ?: 0f) / 100f * 50).coerceIn(0f,50f)
        (lightScore + humidityScore).toInt()
    }

    // Day/night pozadina
    val backgroundColor by animateColorAsState(
        targetValue = if ((0..23).random() in 6..18) Color(0xFFB3E5FC) else Color(0xFF0D47A1)
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Plantico") },
                actions = {
                    IconButton(onClick = onRefresh) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = backgroundColor
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Lottie animacija biljke
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(250.dp)
            )

            // Health score
            Text(
                text = "Health: $healthScore/100",
                style = MaterialTheme.typography.titleMedium,
                color = if (healthScore > 60) Color.Green else Color.Red
            )
            LinearProgressIndicator(
                progress = healthScore / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            // Indikatori senzora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Svjetlo", style = MaterialTheme.typography.bodyMedium)
                    Text("${String.format("%.0f", light ?: 0f)} lx")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Vlaga", style = MaterialTheme.typography.bodyMedium)
                    Text("${String.format("%.0f", humidity ?: 0f)}%")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Gumb za zalijevanje
            Button(
                onClick = onWater,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.WaterDrop, contentDescription = "Water")
                Spacer(Modifier.width(8.dp))
                Text("Zalij biljku")
            }
        }
    }
}
*/