package etf.ri.rma.newsfeedapp.plantico.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import etf.ri.rma.newsfeedapp.plantico.network.SensorApi
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
}

/*
fun refreshNow() {
    viewModelScope.launch {
        try {
            val respL = api.getLight(); _light.value = respL.light
            val respH = api.getHumidity(); _humidity.value = respH.humidity
        } catch (_: Exception) { }
    }
}

fun waterPlant() {
    viewModelScope.launch {
        // npr. POST("/api/water") ili lokalna logika
    }
}
*/
