package tgo1014.webgados.contracts

import tgo1014.webgados.base.BasePresenter
import tgo1014.webgados.model.database.AdDatabase
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
        fun attachView(view: DetailView, database: AdDatabase)
        fun adObtained(idAd: Int)
        fun onBackButtonSelected()
    }

    interface DetailModel {
        fun initDb(database: AdDatabase)
        fun getAd(adId: Int): Ad?
    }
}