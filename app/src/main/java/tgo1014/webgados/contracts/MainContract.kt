package tgo1014.webgados.contracts

import android.content.res.Resources
import tgo1014.webgados.base.BasePresenter
import tgo1014.webgados.model.dao.AdDao
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad

interface MainContract {

    interface MainView {
        fun showLoading()
        fun hideLoading()
        fun hideSwipeLoading()
        fun showAds(adList: List<Ad>, gridSize: Int)
        fun restoreRecyclerViewPosition()
        fun saveRecyclerViewPosition()
        fun showLoadingToolbar()
        fun hideLoadingToolbar()
        fun messageNoMoreAds()
        fun errorCantLoadMoreAds()
    }

    interface MainPresenter : BasePresenter<MainView> {
        fun attachView(view: MainView, database: AdDatabase, resources: Resources)
        fun onSnackBarClicked()
        fun loadMoreAds()
        fun onSwipeToRefresh()
        fun onDestroy(adList: List<Ad>)
        fun onPause()
    }

    interface MainModel : AdDao {
        interface OnAdsRequestCompletionListener {
            fun onSuccess(adList: List<Ad>)
            fun onError(adList: List<Ad>?)
        }

        fun initDb(database: AdDatabase)
        fun getAll(forceOnline: Boolean, listener: OnAdsRequestCompletionListener)
    }
}