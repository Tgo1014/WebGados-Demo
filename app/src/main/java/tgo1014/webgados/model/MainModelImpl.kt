package tgo1014.webgados.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.network.RestClient
import tgo1014.webgados.network.api_results.AdsApiResult

class MainModelImpl : MainContract.MainModel {

    private var adsCache: MutableList<Ad> = ArrayList()

    override fun saveAdsCache(AdList: List<Ad>) {
        clearAdsCache()
        this.adsCache.addAll(AdList)
    }

    override fun clearAdsCache() {
        this.adsCache.clear()
    }

    override fun restoreAdsCache(): List<Ad> = adsCache

    override fun requestAds(listener: MainContract.MainModel.OnAdsRequestCompletionListener) {
        RestClient.getAds().enqueue(object : Callback<AdsApiResult> {
            override fun onResponse(call: Call<AdsApiResult>, response: Response<AdsApiResult>) {
                if (response.isSuccessful) {
                    listener.onSucess(response.body()!!)
                    return
                }

                listener.onError("Error") //TODO ajustar mensagem neste caso
            }

            override fun onFailure(call: Call<AdsApiResult>, t: Throwable) {
                listener.onError(t.localizedMessage)
            }
        })
    }
}