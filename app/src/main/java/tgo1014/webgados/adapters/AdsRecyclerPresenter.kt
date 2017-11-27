package tgo1014.webgados.adapters

import tgo1014.webgados.contracts.AdsRecyclerContract
import tgo1014.webgados.model.objects.Ad
import java.text.NumberFormat
import java.util.*

class AdsRecyclerPresenter(private var adsList: List<Ad>) : AdsRecyclerContract.ShotRowPresenter {

    var adView: AdsRecyclerContract.AdRowView? = null

    override fun attachView(view: AdsRecyclerContract.AdRowView) {
        this.adView = view
    }

    override fun detachView() {
        this.adView = null
    }

    override fun onItemSaveClick() {
        adView?.showSaveMessage()
    }

    override fun onItemSendMessageClick() {
        adView?.showSendMessageMessage()
    }

    override fun onItemBodyClick(holder: AdsViewHolder, adapterPosition: Int) {
        holder.startDetailActivity(adsList[adapterPosition].id.toInt())
    }

    override fun onBindShotRowViewAtPosition(holder: AdsViewHolder, position: Int) {

        //Idade
        var idade = ""
        if (adsList[position].years.isNotEmpty())
            idade += adsList[position].years + if (adsList[position].years.toInt() <= 1) " ano " else " anos "
        if (adsList[position].months.isNotEmpty())
            idade += adsList[position].months + if (adsList[position].months.toInt() <= 1) " mês " else " meses "
        if (idade.isBlank()) idade = "-"

        //Peso
        val peso = if (adsList[position].average_weight.isBlank()) "-" else "${adsList[position].average_weight} ${adsList[position].weight_type}"

        holder.setData(adsList[position].photos_url[0],
                adsList[position].title,
                idade, //rever
                peso,
                NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(adsList[position].unit_value.toInt() / 100),
                adsList[position].seller_avatar,
                adsList[position].seller_name,
                adsList[position].seller_rating,
                "${adsList[position].farm.city} - ${adsList[position].farm.state}",
                "30 km",
                adsList[position].price_type)
    }

    override fun getShotRowsCount(): Int = adsList.size
}