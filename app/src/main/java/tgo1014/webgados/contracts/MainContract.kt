package tgo1014.webgados.contracts

import tgo1014.webgados.base.BasePresenter
import tgo1014.webgados.model.dao.AdDao
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad

interface MainContract {

    interface MainView {
        fun showLoading()
        fun hideLoading()
        fun showAds(adList: List<Ad>)
        fun showError(error: String)
    }

    interface MainPresenter : BasePresenter<MainView> {
        fun attachView(view: MainView, database: AdDatabase)
        fun onSnackBarClicked()
        fun loadMoreAds()
        fun onDestroy(adList: List<Ad>)
    }

    interface MainModel : AdDao {
        interface OnAdsRequestCompletionListener {
            fun onSucess(adList: List<Ad>)
            fun onError(error: String)
        }

        fun initDb(database: AdDatabase)
        fun getAll(forceOnline: Boolean, listener: OnAdsRequestCompletionListener)
    }
}