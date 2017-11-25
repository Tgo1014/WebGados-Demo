package tgo1014.webgados.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ad.view.*
import tgo1014.webgados.R
import tgo1014.webgados.contracts.AdsRecyclerContract
import tgo1014.webgados.ui.DetailActivity

class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), AdsRecyclerContract.ShotRowView, View.OnClickListener {

    private lateinit var list_item_image: ImageView
    private lateinit var list_item_titulo: TextView
    private lateinit var list_item_idade: TextView
    private lateinit var list_item_peso: TextView
    private lateinit var list_item_preco: TextView
    private lateinit var list_item_avatar: ImageView
    private lateinit var list_item_nome_corretor: TextView
    private lateinit var list_item_avaliacao_corretor: TextView
    private lateinit var list_item_cidade_estado: TextView
    private lateinit var list_item_distancia: TextView
    private lateinit var list_item_tipo_preco: TextView

    private lateinit var presenter: AdsRecyclerContract.ShotRowPresenter

    private var AD_ID_EXTRA = "adIdExtra"

    constructor(presenter: AdsRecyclerContract.ShotRowPresenter, itemView: View) : this(itemView) {
        this.presenter = presenter
        this.list_item_image = itemView.list_item_image
        this.list_item_titulo = itemView.list_item_titulo
        this.list_item_idade = itemView.list_item_idade
        this.list_item_peso = itemView.list_item_peso
        this.list_item_preco = itemView.list_item_preco
        this.list_item_avatar = itemView.list_item_avatar
        this.list_item_nome_corretor = itemView.list_item_nome_corretor
        this.list_item_avaliacao_corretor = itemView.list_item_avaliacao_corretor
        this.list_item_cidade_estado = itemView.list_item_cidade_estado
        this.list_item_distancia = itemView.list_item_distancia
        this.list_item_tipo_preco = itemView.list_item_tipo_preco
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        presenter.onItemClick(this@AdsViewHolder, adapterPosition)
    }

    override fun setData(imageUrl: String,
                         titulo: String,
                         idade: String,
                         peso: String,
                         preco: String,
                         avatar: String,
                         nomeCorretor: String,
                         avaliacaoCorretor: String,
                         cidadedEstado: String,
                         distancia: String,
                         tipo_preco: String) {

        if (imageUrl.isNotBlank())
            Picasso.with(itemView.context)
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .into(list_item_image)

        if (avatar.isNotBlank())
            Picasso.with(itemView.context)
                    .load(avatar)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .into(list_item_avatar)
        else
            Picasso.with(itemView.context)
                    .load(R.drawable.no_avatar)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.loading_animation)
                    .into(list_item_avatar)

        list_item_titulo.text = titulo
        list_item_idade.text = idade
        list_item_peso.text = peso
        list_item_preco.text = preco
        list_item_nome_corretor.text = nomeCorretor
        list_item_avaliacao_corretor.text = avaliacaoCorretor
        list_item_cidade_estado.text = cidadedEstado
        list_item_distancia.text = distancia
        list_item_tipo_preco.text = tipo_preco
    }

    override fun startDetailActivity(idShotDetail: Int) {
        val intent = Intent(itemView.context, DetailActivity::class.java)
        intent.putExtra(AD_ID_EXTRA, idShotDetail)
        itemView.context.startActivity(intent)
    }
}