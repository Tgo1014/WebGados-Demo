package tgo1014.webgados.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tgo1014.webgados.BuildConfig
import tgo1014.webgados.network.api_results.AdsApiResult

object RestClient {

    private val WEB_GADO_API: WebGadoApi

    init {
        val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val clientWithInterceptor = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://webgados.com.br/")
                .client(if (BuildConfig.DEBUG) clientWithInterceptor else OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        WEB_GADO_API = retrofit.create(WebGadoApi::class.java)
    }

    fun getAds(): Call<AdsApiResult> = WEB_GADO_API.getAds()
}