package tgo1014.webgados.base

import android.support.v7.app.AppCompatActivity
import tgo1014.webgados.WebGadosApplication
import tgo1014.webgados.model.database.AdDatabase

abstract class BaseMvpActivity<P : BasePresenter<V>, V> : AppCompatActivity() {

    private var presenter: P? = null
    private fun getPresenterView(): V = this as V

    override fun onStart() {
        super.onStart()
        presenter?.attachView(getPresenterView())
    }

    override fun onStop() {
        presenter?.detachView()
        super.onStop()
    }

    fun getDatabaseInstance(): AdDatabase = (application as WebGadosApplication).adDatabase
}
