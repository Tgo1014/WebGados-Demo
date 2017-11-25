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

    override fun onDestroy(ad: Ad?) {
        detailModel.saveAdsCache(ad)
    }

    override fun adObtained(idAd: Int) {
        requestAd(idAd)
    }

    override fun onOptionsItemSelected(itemId: Int) {
        when (itemId) {
            android.R.id.home -> detailView?.onBackPressed()
        }
    }

    private fun requestAd(AdId: Int) {
        if (detailModel.restoreAdsCache() != null) {
            detailView?.hideLoadingToolbar()
            detailView?.showAdDetail(detailModel.restoreAdsCache()!!)
            return
        }

        detailModel.requestAd(AdId, object : DetailContract.DetailModel.OnAdRequestCompletionListener {
            override fun onSucess(Ad: Ad) {
                detailView?.hideLoadingToolbar()
                detailView?.showAdDetail(Ad)
            }

            override fun onError(error: String) {
                detailView?.hideLoadingToolbar()
                detailView?.showError()
            }
        })
    }
}