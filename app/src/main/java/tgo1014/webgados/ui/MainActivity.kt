package tgo1014.webgados.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import tgo1014.webgados.R
import tgo1014.webgados.adapters.AdsRecyclerAdapter
import tgo1014.webgados.adapters.AdsRecyclerPresenter
import tgo1014.webgados.base.BaseMvpActivity
import tgo1014.webgados.contracts.MainContract
import tgo1014.webgados.listeners.GridEndlessRecyclerViewScrollListener
import tgo1014.webgados.model.MainModelImpl
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.presenters.MainPresenterImpl

class MainActivity : BaseMvpActivity<MainContract.MainPresenter, MainContract.MainView>(), MainContract.MainView {

    private var mainPresenter: MainContract.MainPresenter? = null
    private lateinit var mainRecyclerAdapter: AdsRecyclerAdapter
    private var mainRecyclerAdList: MutableList<Ad> = ArrayList()
    private lateinit var gridLayoutManager: GridLayoutManager
    private var lastRecyclerViewItemPosition: Int = -1
    private var recyclerViewTop = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        supportActionBar?.title = getString(R.string.app_name)

        mainPresenter = MainPresenterImpl(MainModelImpl())

        mainSwipeToRefresh.setOnRefreshListener { mainPresenter?.onSwipeToRefresh() }
    }

    override fun onResume() {
        super.onResume()
        mainPresenter?.attachView(this, getDatabaseInstance(), resources)
        restoreRecyclerViewPosition()
    }

    override fun onDestroy() {
        mainPresenter?.onDestroy(mainRecyclerAdList)
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mainPresenter?.onPause()
    }

    override fun messageNoMoreAds() {
        showMessage(getString(R.string.str_no_more_ads), false)
    }

    override fun errorCantLoadMoreAds() {
        showMessage(getString(R.string.str_error_loading_ads), true, Snackbar.LENGTH_INDEFINITE)
    }

    override fun saveRecyclerViewPosition() {
        lastRecyclerViewItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
        val v = mainRecycler.getChildAt(0)
        recyclerViewTop = if (v == null) 0 else v.top - mainRecycler.paddingTop
    }

    override fun restoreRecyclerViewPosition() {
        if (lastRecyclerViewItemPosition != -1)
            gridLayoutManager.scrollToPositionWithOffset(lastRecyclerViewItemPosition, recyclerViewTop)
    }

    override fun showLoading() {
        mainRecycler.visibility = View.GONE
        mainProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mainRecycler.visibility = View.VISIBLE
        mainProgressBar.visibility = View.GONE
    }

    override fun showAds(adList: List<Ad>, gridSize: Int) {

        gridLayoutManager = GridLayoutManager(this, gridSize)

        mainRecyclerAdList.clear()
        mainRecyclerAdList.addAll(adList)
        mainRecyclerAdapter = AdsRecyclerAdapter(AdsRecyclerPresenter(mainRecyclerAdList))
        mainRecycler.adapter = mainRecyclerAdapter
        mainRecycler.layoutManager = gridLayoutManager
        mainRecycler.addOnScrollListener(
                GridEndlessRecyclerViewScrollListener(
                        gridLayoutManager,
                        GridEndlessRecyclerViewScrollListener.DataLoader {
                            mainPresenter?.loadMoreAds()
                            true
                        }))
    }

    override fun showLoadingToolbar() {
        mainTollbarProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingToolbar() {
        mainTollbarProgressBar.visibility = View.GONE
    }

    override fun hideSwipeLoading() {
        mainSwipeToRefresh.isRefreshing = false
    }

    /**
     * Mostra um snackbar com a mensagem informada
     *
     * @param message Mensagem que será exibida para o usuário
     * @param showTryAgain exibe ou não a opção para o usuário tentar novamente
     * @param snackbarDuration tempo em que o snackbar sera exibido
     */
    private fun showMessage(message: String, showTryAgain: Boolean, snackbarDuration: Int = Snackbar.LENGTH_LONG) {
        val snack = Snackbar.make(findViewById(R.id.mainLinearLayout), message, snackbarDuration)
        if (showTryAgain) snack.setAction(getString(R.string.str_try_again), { mainPresenter?.onSnackBarClicked() })
        snack.show()
    }
}


