package tgo1014.webgados.contracts

import tgo1014.webgados.base.BasePresenter
import tgo1014.webgados.model.objects.Ad

interface DetailContract {

    interface DetailView {
        fun showAdDetail(ad: Ad)
        fun showError()
        fun getAdIdExtra()
        fun showLoadingToolbar()
        fun hideLoadingToolbar()
        fun onBackPressed()
    }

    interface DetailPresenter : BasePresenter<DetailView> {
        fun onDestroy(ad: Ad?)
        fun adObtained(idAd: Int)
        fun onOptionsItemSelected(itemId: Int)
    }

    interface DetailModel {
        interface OnAdRequestCompletionListener {
            fun onSucess(ad: Ad)
            fun onError(error: String)
        }

        fun requestAd(adId: Int, listener: OnAdRequestCompletionListener)
        fun saveAdsCache(ad: Ad?)
        fun restoreAdsCache(): Ad?
        fun clearAdsCache()
    }
}