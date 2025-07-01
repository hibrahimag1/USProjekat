package etf.ri.rma.newsfeedapp.plantico.screen



import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import etf.ri.rma.newsfeedapp.plantico.viewModel.SensorViewModel

@Composable
fun PlantScreen(viewModel: SensorViewModel = viewModel()) {
    val context = LocalContext.current
    val light by viewModel.light.collectAsState()
    val humidity by viewModel.humidity.collectAsState()

    // Pozivanje notifikacije kada se promijene vrijednosti
    LaunchedEffect(light, humidity) {
        if (context is Activity && light != null && humidity != null) {
            viewModel.checkAndNotify(context)
        }
    }

    // Prikaz statusa biljke (ekran sa animacijom i progress barovima)
    PlantStatusScreen(
        waterLevel = humidity,
        lightLevel = light
    )
}
