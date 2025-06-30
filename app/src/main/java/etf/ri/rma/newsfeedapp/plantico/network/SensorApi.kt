package etf.ri.rma.newsfeedapp.plantico.network

import retrofit2.http.GET


data class LightResponse(val light: Float)

data class SensorDataResponse(
    val humidity: Float,
    val light: Float
)


interface SensorApi {

    @GET("api/data")
    suspend fun getSensorData(): SensorDataResponse

    companion object {

        private const val BASE_URL = "https://usprojekat.onrender.com/"

        fun create(): SensorApi {
            return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
                .create(SensorApi::class.java)
        }
    }
}