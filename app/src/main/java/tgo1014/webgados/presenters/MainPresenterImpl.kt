package tgo1014.webgados.presenters

import android.content.res.Configuration
import android.content.res.Resources
import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.model.database.AdDatabase
import tgo1014.webgados.model.objects.Ad


class MainPresenterImpl(private var mainModel: MainContract.MainModel) : MainContract.MainPresenter {

    private var mainView: MainContract.MainView? = null
    private var resources: Resources? = null

    override fun attachView(view: MainContract.MainView) {
        this.mainView = view
        requestAds()
    }

    override fun attachView(view: MainContract.MainView, database: AdDatabase, resources: Resources) {
        this.mainView = view
        this.resources = resources

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
        mainView?.messageNoMoreAds()
    }

    override fun onSwipeToRefresh() {
        requestAds(true)
    }

    private fun requestAds(forceOnline: Boolean = false) {
        mainView?.showLoading()
        mainView?.showLoadingToolbar()
        mainModel.getAll(forceOnline, object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSuccess(adList: List<Ad>) {
                hideLoadinsInMainView()
                mainView?.showAds(adList, calcImageGridSizeByOrientation())
            }

            override fun onError(adList: List<Ad>?) {
                hideLoadinsInMainView()
                adList?.run { mainView?.showAds(this, calcImageGridSizeByOrientation()) }
                mainView?.errorCantLoadMoreAds()
            }
        })
    }

    /**
     * Escondo todos os loadings na view
      */
    private fun hideLoadinsInMainView() {
        mainView?.hideLoading()
        mainView?.hideSwipeLoading()
        mainView?.hideLoadingToolbar()
    }

    /**
     * Calcula a quantidade de itens que serão exibido na tela baseado na orientação do dispositivo
     */
    private fun calcImageGridSizeByOrientation(): Int {
        if (resources!!.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 2
        return 1
    }
}