package tgo1014.webgados.network

import retrofit2.Call
import retrofit2.http.GET
import tgo1014.webgados.network.api_results.AdsApiResult

interface WebGadosApi {
    @GET("anuncios-example")
    fun getAds(): Call<AdsApiResult>
}