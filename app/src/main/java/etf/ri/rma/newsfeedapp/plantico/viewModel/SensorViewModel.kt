package etf.ri.rma.newsfeedapp.plantico.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import etf.ri.rma.newsfeedapp.plantico.network.SensorApi
import etf.ri.rma.newsfeedapp.plantico.notification.NotificationHelper
import etf.ri.rma.newsfeedapp.plantico.viewModel.SensorViewModel.Companion.HUMIDITY_THRESHOLD
import etf.ri.rma.newsfeedapp.plantico.viewModel.SensorViewModel.Companion.LIGHT_THRESHOLD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SensorViewModel(
    private val api: SensorApi = SensorApi.create()
) : ViewModel() {

    companion object {
        private const val TAG = "SensorViewModel"
        const val LIGHT_THRESHOLD = 500f    // lx
        const val HUMIDITY_THRESHOLD = 30f  // %
    }

    private var sentLowLightNotification = false
    private var sentLowHumidityNotification = false

    private val _light = MutableStateFlow<Float?>(null)
    val light: StateFlow<Float?> = _light

    private val _humidity = MutableStateFlow<Float?>(null)
    val humidity: StateFlow<Float?> = _humidity

    init {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = api.getSensorData()
                    _light.value = response.light
                    _humidity.value = response.humidity
                } catch (e: Exception) {
                    Log.e(TAG, "Greška pri dohvaćanju podataka senzora: ${e.message}", e)
                }
                delay(2_000)
            }
        }
    }

    fun refreshNow() {
        viewModelScope.launch {
            try {
                val response = api.getSensorData()
                _light.value = response.light
                _humidity.value = response.humidity
            } catch (e: Exception) {
                Log.e(TAG, "Greška pri ručnom osvježavanju podataka: ${e.message}", e)
            }
        }
    }
    fun checkAndNotify(context: Context) {
        val lightValue = light.value
        val humidityValue = humidity.value

        // Svjetlost
        if (lightValue != null && lightValue < LIGHT_THRESHOLD) {
            if (!sentLowLightNotification) {
                NotificationHelper.sendNotification(
                    context,
                    "Nizak nivo svjetlosti",
                    "Vašoj biljci treba više svjetlosti!",
                    id = 1
                )
                sentLowLightNotification = true
            }
        } else {
            // Resetujemo flag kad se stanje poboljša
            sentLowLightNotification = false
        }

        // Vlažnost
        if (humidityValue != null && humidityValue < HUMIDITY_THRESHOLD) {
            if (!sentLowHumidityNotification) {
                NotificationHelper.sendNotification(
                    context,
                    "Niska vlažnost tla",
                    "Jesaam žedaaaan, jesaaam žedan",
                    id = 2
                )
                sentLowHumidityNotification = true
            }
        } else {
            sentLowHumidityNotification = false
        }
    }

}



