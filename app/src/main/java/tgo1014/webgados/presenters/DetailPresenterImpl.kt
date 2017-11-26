package tgo1014.webgados.presenters

import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.objects.Ad

class DetailPresenterImpl(private var detailModel: DetailContract.DetailModel) : DetailContract.DetailPresenter {

    private var detailView: DetailContract.DetailView? = null

    override fun attachView(view: DetailContract.DetailView) {
        this.detailView = view
        detailView?.showLoadingToolbar()
        detailView?.getAdIdExtra()
    }

    override fun detachView() {
        this.detailView = null
    }

    override fun adObtained(idAd: Int) {
        requestAd(idAd)
    }

    private fun requestAd(AdId: Int) {

        detailModel.requestAd(AdId, object : DetailContract.DetailModel.OnAdRequestCompletionListener {
            override fun onSucess(ad: Ad) {
                detailView?.hideLoadingToolbar()
                detailView?.showAdDetail(ad)
            }

            override fun onError(error: String) {
                detailView?.hideLoadingToolbar()
                detailView?.showError()
            }
        })
    }
}