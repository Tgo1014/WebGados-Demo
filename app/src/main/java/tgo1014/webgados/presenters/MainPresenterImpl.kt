package tgo1014.webgados.presenters

import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad


class MainPresenterImpl(private var mainModel: MainContract.MainModel) : MainContract.MainPresenter {

    private var mainView: MainContract.MainView? = null

    override fun attachView(view: MainContract.MainView) {
        this.mainView = view
        requestAds()
    }

    override fun attachView(view: MainContract.MainView, database: AdDatabase) {
        this.mainView = view
        mainModel.initDb(database)
        requestAds()
        mainView?.restoreRecyclerViewPosition()
    }

    override fun detachView() {
        mainView = null
    }

    override fun onPause() {
        mainView?.saveRecyclerViewPosition()
    }

    override fun onDestroy(adList: List<Ad>) {
        //TODO
    }

    override fun onSnackBarClicked() {
        mainView?.showLoading()
        requestAds(true)
    }

    override fun loadMoreAds() {
        //TODO
    }

    override fun onSwipeToRefresh() {
        requestAds(true)
    }

    private fun requestAds(forceOnline: Boolean = false) {
        mainModel.getAll(forceOnline, object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSucess(adList: List<Ad>) {
                mainView?.hideLoading()
                mainView?.hideSwipeLoading()
                mainView?.showAds(adList)
            }

            override fun onError(adList: List<Ad>?, error: String) {
                mainView?.hideLoading()
                mainView?.hideSwipeLoading()
                adList?.run { mainView?.showAds(this) }
                mainView?.showError(error)
            }
        })
    }
}