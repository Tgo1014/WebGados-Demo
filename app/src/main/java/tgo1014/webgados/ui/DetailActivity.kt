package tgo1014.webgados.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import tgo1014.webgados.R
import tgo1014.webgados.base.BaseMvpActivity
import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.objects.Ad

class DetailActivity : BaseMvpActivity<DetailContract.DetailPresenter, DetailContract.DetailView>(), DetailContract.DetailView {

    private var detailPresenter: DetailContract.DetailPresenter? = null
    private var Ad_ID_EXTRA = "AdIdExtra"
    private var ad: Ad? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val upArrow: Drawable = resources.getDrawable(R.drawable.abc_ic_ab_back_material)
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    override fun onDestroy() {
        super.onDestroy()
        detailPresenter?.onDestroy(ad)
    }

    override fun showLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        detailPresenter?.onOptionsItemSelected(item.itemId)
        return true
    }

    override fun hideLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.GONE
    }

    override fun showAdDetail(Ad: Ad) {

        this.ad = Ad
        supportActionBar?.title = Ad.title

        detailTollbarProgressBar.visibility = View.VISIBLE

//        Picasso.with(this)
//                .load(Ad.images.hidpi)
//                .fit()
//                .centerInside()
//                .placeholder(R.drawable.dribbble_logo)
//                .into(detailImageView, object : Callback {
//                    override fun onSuccess() {
//                        detailTollbarProgressBar.visibility = View.GONE
//                    }
//
//                    override fun onError() {
//                        detailTollbarProgressBar.visibility = View.GONE
//                        Toast.makeText(this@DetailActivity, "Unable to load image", Toast.LENGTH_SHORT).show()
//                    }
//                })
//
//        Picasso.with(this)
//                .load(ad.photos_url[0])
//                .fit()
//                .centerInside()
//                .placeholder(R.drawable.dribbble_logo)
//                .into(detailAvatar)

//        detailTxtComments.text = Ad.comments_count.toString()
//        detailTxtFavorites.text = Ad.likes_count.toString()
//        detailTxtViews.text = Ad.views_count.toString()
//        detailUserName.text = Ad.user.name
//        detailUserUsername.text = Ad.user.username
//        detailDescription.text = Html.fromHtml(Ad.description ?: "")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any = detailPresenter!!

    override fun showError() {

    }

    override fun getAdIdExtra() {
        detailPresenter?.adObtained(intent.getIntExtra(Ad_ID_EXTRA, -1))
    }
}
