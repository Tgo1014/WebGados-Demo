package tgo1014.webgados.model

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.network.RestClient
import tgo1014.webgados.network.api_results.AdsApiResult

class MainModelImpl : MainContract.MainModel {

    private lateinit var database: AdDatabase

    override fun initDb(database: AdDatabase) {
        this.database = database
    }

    override fun getAll(): List<Ad> = runBlocking {
        return@runBlocking async { database.adDao().getAll() }.await()
    }

    override fun getById(id: Int): Ad = runBlocking {
        return@runBlocking async { database.adDao().getById(id) }.await()
    }

    override fun insert(ad: Ad) = runBlocking {
        return@runBlocking async { database.adDao().insert(ad) }.await()
    }

    override fun deleteAd(ad: Ad) = runBlocking {
        return@runBlocking async { database.adDao().deleteAd(ad) }.await()
    }

    private fun insertAll(adList: List<Ad>) = runBlocking {
        for (ad in adList) insert(ad)
    }

    override fun getAll(forceOnline: Boolean, listener: MainContract.MainModel.OnAdsRequestCompletionListener) = runBlocking {
        val adListCache: List<Ad> = getAll()

        if (!forceOnline) {
            if (adListCache.isNotEmpty()) {
                listener.onSuccess(adListCache)
                return@runBlocking
            }
        }

        requestAdsFromServer(object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSuccess(adList: List<Ad>) = runBlocking {
                insertAll(adList)
                listener.onSuccess(adList)
            }

            override fun onError(adList: List<Ad>?) {
                listener.onError(adListCache)
            }
        })
    }

    private fun requestAdsFromServer(listener: MainContract.MainModel.OnAdsRequestCompletionListener) {
        RestClient.getAds().enqueue(object : Callback<AdsApiResult> {
            override fun onResponse(call: Call<AdsApiResult>, response: Response<AdsApiResult>) {
                if (response.isSuccessful) {
                    listener.onSuccess(response.body()?.ads!!)
                    return
                }

                listener.onError(null) //TODO ajustar mensagem neste caso
            }

            override fun onFailure(call: Call<AdsApiResult>, t: Throwable) {
                listener.onError(null)
            }
        })
    }
}
