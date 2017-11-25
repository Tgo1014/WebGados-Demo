package tgo1014.webgados.contracts

import tgo1014.webgados.base.BasePresenter
import tgo1014.webgados.model.dao.AdDao
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.network.api_results.AdsApiResult

interface MainContract {

    interface MainView {
        fun showLoading()
        fun hideLoading()
        fun showAds(adList: List<Ad>)
        fun showError(error: String)
    }

    interface MainPresenter : BasePresenter<MainView> {
        fun onAttachView(view: MainView, database: AdDatabase)
        fun onSnackBarClicked()
        fun loadMoreAds()
        fun onDestroy(adList: List<Ad>)
    }

    interface MainModel : AdDao {

        fun initDb(database: AdDatabase)

        interface OnAdsRequestCompletionListener {
            fun onSucess(adList: AdsApiResult)
            fun onError(error: String)
        }

        fun requestAds(listener: OnAdsRequestCompletionListener)
        fun saveAdsCache(adList: List<Ad>)
        fun restoreAdsCache(): List<Ad>
        fun clearAdsCache()
    }
}