package tgo1014.webgados.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tgo1014.webgados.R
import tgo1014.webgados.contracts.AdsRecyclerContract

class AdsRecyclerAdapter(private var presenter: AdsRecyclerContract.ShotRowPresenter) : RecyclerView.Adapter<AdsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return AdsViewHolder(presenter, LayoutInflater.from(parent.context).inflate(R.layout.item_ad, parent, false))
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        presenter.onBindShotRowViewAtPosition(holder, position)
    }

    override fun getItemCount(): Int = presenter.getShotRowsCount()
}