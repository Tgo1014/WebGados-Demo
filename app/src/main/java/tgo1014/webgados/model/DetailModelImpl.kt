package tgo1014.webgados.model

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.database.AdDatabase


class DetailModelImpl : DetailContract.DetailModel {

    var adDb: AdDatabase? = null

    override fun initDb(database: AdDatabase) {
        this.adDb = database
    }

    override fun getAd(adId: Int) = runBlocking {
        return@runBlocking async { adDb?.adDao()?.getById(adId) }.await()
    }
}