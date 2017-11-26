package tgo1014.webgados.contracts

import tgo1014.webgados.adapters.AdsViewHolder
import tgo1014.webgados.base.BasePresenter

interface AdsRecyclerContract {

    interface AdRowView {
        fun setData(imageUrl: String,
                    titulo: String,
                    idade: String,
                    peso: String,
                    preco: String,
                    avatar: String,
                    nomeCorretor: String,
                    avaliacaoCorretor: String,
                    cidadedEstado: String,
                    distancia: String,
                    tipo_preco: String)

        fun startDetailActivity(adDetail: Int)
        fun showToast(message: String)
    }

    interface ShotRowPresenter : BasePresenter<AdRowView> {
        fun onBindShotRowViewAtPosition(holder: AdsViewHolder, position: Int)
        fun getShotRowsCount(): Int
        fun onItemSaveClick()
        fun onItemSendMessageClick()
        fun onItemBodyClick(holder: AdsViewHolder, adapterPosition: Int)
    }
}