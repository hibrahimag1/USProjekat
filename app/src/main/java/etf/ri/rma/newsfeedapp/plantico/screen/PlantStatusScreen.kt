package etf.ri.rma.newsfeedapp.plantico.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import etf.ri.rma.newsfeedapp.plantico.R
import kotlin.math.roundToInt

@Composable
fun PlantStatusScreen(
    waterLevel: Float?,
    lightLevel: Float?
) {

    val wLevel : Int = waterLevel?.roundToInt() ?: 0
    val lLevel : Int = lightLevel?.roundToInt() ?: 0
    val isHappy = wLevel >= 20.0 && lLevel >= 30.0

    val happyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.happy_flower))
    val sadComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sad_plant))

    val progress by animateLottieCompositionAsState(
        composition = if (isHappy) happyComposition else sadComposition,
        iterations = LottieConstants.IterateForever
    )

    // Pozadina i boja teksta
    val backgroundColor = if (lLevel >= 40) Color(0xFFE0F7FA) else Color(0xFF263238)
    val textColor = if (lLevel >= 40) Color.Black else Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // za manje ekrane
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = if (isHappy) "Biljka je sretna! 🌼" else "⚠️ Biljka je tužna",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        LottieAnimation(
            composition = if (isHappy) happyComposition else sadComposition,
            progress = progress,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("💧 Nivo vode: $wLevel%", fontSize = 16.sp, color = textColor)
        LinearProgressIndicator(
            progress = wLevel / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            color = Color.Cyan
        )

        Text("☀️ Nivo svjetlosti: $lLevel%", fontSize = 16.sp, color = textColor)
        LinearProgressIndicator(
            progress = lLevel / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            color = Color.Yellow
        )
    }
}
