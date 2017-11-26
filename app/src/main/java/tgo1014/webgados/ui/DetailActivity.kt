package tgo1014.webgados.ui

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.daimajia.slider.library.Indicators.PagerIndicator
import com.daimajia.slider.library.SliderTypes.DefaultSliderView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import tgo1014.webgados.R
import tgo1014.webgados.base.BaseMvpActivity
import tgo1014.webgados.contracts.DetailContract
import tgo1014.webgados.model.DetailModelImpl
import tgo1014.webgados.model.objects.Ad
import tgo1014.webgados.presenters.DetailPresenterImpl
import java.text.NumberFormat
import java.util.*

class DetailActivity : BaseMvpActivity<DetailContract.DetailPresenter, DetailContract.DetailView>(), DetailContract.DetailView {

    private var detailPresenter: DetailContract.DetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val upArrow: Drawable = resources.getDrawable(R.drawable.abc_ic_ab_back_material)
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        detailPresenter = DetailPresenterImpl(DetailModelImpl())
        detailPresenter?.attachView(this, getDatabaseInstance())
    }

    override fun showLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> detailPresenter?.onBackButtonSelected()
        }
        return true
    }

    override fun hideLoadingToolbar() {
        detailTollbarProgressBar.visibility = View.GONE
    }

    override fun showAdDetail(ad: Ad) {

        supportActionBar?.title = ad.title

        val slider = detailBannerSlide

        for (photo in ad.photos_url)
            slider.addSlider(DefaultSliderView(this).apply { image(photo) })

        //if there's only one photo, stop auto rotating
        if (ad.photos_url.size == 1) {
            slider.stopAutoCycle()
            slider.indicatorVisibility = PagerIndicator.IndicatorVisibility.Invisible
        }


        if (ad.seller_avatar.isNotBlank())
            Picasso.with(this)
                    .load(ad.seller_avatar)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .into(detail_item_avatar)
        else
            Picasso.with(this)
                    .load(R.drawable.no_avatar)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .into(detail_item_avatar)

        //Idade
        var idade = ""
        if (ad.years.isNotEmpty())
            idade += ad.years + if (ad.years.toInt() <= 1) " ano " else " anos "
        if (ad.months.isNotEmpty())
            idade += ad.months + if (ad.months.toInt() <= 1) " mês " else " meses "
        if (idade.isBlank()) idade = "-"

        //Peso
        val peso = if (ad.average_weight.isBlank()) "-" else "${ad.average_weight} ${ad.weight_type}"

        detail_item_titulo.text = ad.title
        detail_item_idade.text = idade
        detail_item_peso.text = peso
        detail_item_preco.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(ad.unit_value.toInt() / 100)
        detail_item_nome_corretor.text = ad.seller_name
        detail_item_avaliacao_corretor.text = ad.seller_rating
        detail_item_cidade_estado.text = "${ad.farm.city} - ${ad.farm.state}"
        detail_item_distancia.text = "30km"
        detail_item_tipo_preco.text = ad.price_type
    }

    override fun onStop() {
        detailBannerSlide.stopAutoCycle() //stop auto rotating to avoid memory leaks
        super.onStop()
    }

    override fun showError() {
        //TODO
    }

    override fun getAdIdExtra() {
        detailPresenter?.adObtained(intent.getIntExtra(Ad.AD_ID_EXTRA, -1))
    }
}
