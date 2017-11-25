package tgo1014.webgados.contracts

import tgo1014.webgados.adapters.AdsViewHolder

interface AdsRecyclerContract {

    interface ShotRowView {
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

        fun startDetailActivity(idShotDetail: Int)
    }

    interface ShotRowPresenter {
        fun onBindShotRowViewAtPosition(holder: AdsViewHolder, position: Int)
        fun getShotRowsCount(): Int
        fun onItemClick(holder: AdsViewHolder, adapterPosition: Int)
    }
}