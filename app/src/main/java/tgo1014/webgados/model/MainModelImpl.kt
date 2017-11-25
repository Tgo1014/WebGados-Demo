package tgo1014.webgados.model

import android.util.Log
import kotlinx.coroutines.experimental.launch
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

    override fun getAll(): List<Ad> = database.adDao().getAll()

    override fun getById(id: Int): Ad = database.adDao().getById(id)

    override fun insert(ad: Ad) = database.adDao().insert(ad)

    override fun deleteAd(ad: Ad) = database.adDao().deleteAd(ad)

    private fun insertAll(adList: List<Ad>) = runBlocking<Unit> {
        var job = launch { for (ad in adList) insert(ad) }
        job.join()
    }

    override fun getAll(forceOnline: Boolean, listener: MainContract.MainModel.OnAdsRequestCompletionListener) = runBlocking<Unit> {
        var adList: List<Ad> = ArrayList()

        val job = launch { adList = getAll() }
        job.join()

        if (!forceOnline) {
            if (adList.isNotEmpty()) {
                Log.e("sdoaskdoaskdoosad", "osdksaodksoakdoas")
                listener.onSucess(adList)
                return@runBlocking
            }
        }

        requestAds(object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSucess(adList: List<Ad>) = runBlocking<Unit> {
                val job = launch { insertAll(adList) }
                job.join()
                listener.onSucess(adList)
            }

            override fun onError(error: String) {
                listener.onError("Não foi possível obter os dados")
            }
        })
    }

    private fun requestAds(listener: MainContract.MainModel.OnAdsRequestCompletionListener) {
        RestClient.getAds().enqueue(object : Callback<AdsApiResult> {
            override fun onResponse(call: Call<AdsApiResult>, response: Response<AdsApiResult>) {
                if (response.isSuccessful) {
                    listener.onSucess(response.body()?.ads!!)
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