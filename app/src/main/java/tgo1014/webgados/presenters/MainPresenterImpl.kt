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
    }

    override fun detachView() {
        mainView = null
    }

    override fun onDestroy(adList: List<Ad>) {
        //TODO
    }

    override fun onSnackBarClicked() {
        mainView?.showLoading()
    }

    override fun loadMoreAds() {
        //TODO
    }

    private fun requestAds() {
        mainModel.getAll(false, object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSucess(adList: List<Ad>) {
                mainView?.hideLoading()
                mainView?.showAds(adList)
            }

            override fun onError(error: String) {
                mainView?.hideLoading()
                mainView?.showError(error)
            }
        })
    }
}