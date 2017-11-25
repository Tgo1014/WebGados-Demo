package tgo1014.webgados.model

import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.objects.Ad


class DetailModelImpl : DetailContract.DetailModel {

    private var AdsCache: Ad? = null

    override fun saveAdsCache(Ad: Ad?) {
        clearAdsCache()
        this.AdsCache = Ad
    }

    override fun clearAdsCache() {
        this.AdsCache = null
    }

    override fun restoreAdsCache(): Ad? = AdsCache

    override fun requestAd(adId: Int, listener: DetailContract.DetailModel.OnAdRequestCompletionListener) {
//        RestClient.getSingleAd(adId).enqueue(object : Callback<Ad> {
//            override fun onResponse(call: Call<Ad>, response: Response<Ad>) {
//                if (response.isSuccessful) {
//                    listener.onSucess(response.body()!!)
//                    return
//                }
//
//                listener.onError("Error") //TODO ajustar mensagem neste caso
//            }
//
//            override fun onFailure(call: Call<Ad>, t: Throwable) {
//                listener.onError(t.localizedMessage)
//            }
//        })
    }
}