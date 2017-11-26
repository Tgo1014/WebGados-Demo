package tgo1014.webgados.presenters

import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.database.AdDatabase

class DetailPresenterImpl(private var detailModel: DetailContract.DetailModel) : DetailContract.DetailPresenter {

    private var detailView: DetailContract.DetailView? = null

    override fun attachView(view: DetailContract.DetailView) {
        //TODO("not used")
    }

    override fun attachView(view: DetailContract.DetailView, database: AdDatabase) {
        this.detailView = view
        detailModel.initDb(database)
        detailView?.getAdIdExtra()
    }

    override fun onBackButtonSelected() {
        detailView?.onBackPressed()
    }

    override fun detachView() {
        this.detailView = null
    }

    override fun adObtained(idAd: Int) {
        detailView?.showAdDetail(detailModel.getAd(idAd)!!)
    }
}