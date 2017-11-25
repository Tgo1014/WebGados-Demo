package tgo1014.webgados.presenters

import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.network.api_results.AdsApiResult


class MainPresenterImpl(private var mainModel: MainContract.MainModel) : MainContract.MainPresenter {

    private var mainView: MainContract.MainView? = null

    override fun attachView(view: MainContract.MainView) {
        this.mainView = view
        requestAds()
    }

    override fun detachView() {
        mainView = null
    }

    override fun onDestroy(adList: List<Ad>) {
        mainModel.saveAdsCache(adList)
    }

    override fun onSnackBarClicked() {
        mainView?.showLoading()
    }

    override fun loadMoreAds() {
             //TODO
    }

    private fun requestAds() {
        mainModel.requestAds(object : MainContract.MainModel.OnAdsRequestCompletionListener {
            override fun onSucess(adList: AdsApiResult) {
                mainView?.hideLoading()
                mainView?.showAds(adList.ads)
            }

            override fun onError(error: String) {
                mainView?.hideLoading()
                mainView?.showError(error)
            }
        })
    }
}