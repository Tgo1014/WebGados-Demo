package tgo1014.webgados.ui

import android.content.res.Configuration
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        supportActionBar?.title = getString(R.string.app_name)
        mainPresenter = MainPresenterImpl(MainModelImpl())

    }

    override fun onResume() {
        super.onResume()
        mainPresenter?.attachView(this)
    }

    override fun onDestroy() {
        mainPresenter?.onDestroy(mainRecyclerAdList)
        super.onDestroy()
    }


    override fun showLoading() {
        mainRecycler.visibility = View.GONE
        mainProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        mainRecycler.visibility = View.VISIBLE
        mainProgressBar.visibility = View.GONE
    }

    override fun showAds(adList: List<Ad>) {
        mainRecyclerAdList.addAll(adList)
        mainRecyclerAdapter = AdsRecyclerAdapter(AdsRecyclerPresenter(mainRecyclerAdList))
        mainRecycler.adapter = mainRecyclerAdapter

        val gridLayoutManager = GridLayoutManager(this, calcImageGridSizeByOrientation())
        mainRecycler.layoutManager = gridLayoutManager

        mainRecycler.addOnScrollListener(
                GridEndlessRecyclerViewScrollListener(
                        gridLayoutManager,
                        GridEndlessRecyclerViewScrollListener.DataLoader {
                            mainPresenter?.loadMoreAds()
                            true
                        }))
    }

    override fun showError(error: String) {
        Snackbar
                .make(findViewById(R.id.mainLinearLayout), getString(R.string.str_error) + ":" + error, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.str_try_again), { mainPresenter?.onSnackBarClicked() })
                .show()
    }

    private fun calcImageGridSizeByOrientation(): Int {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 2
        return 1
    }
}


