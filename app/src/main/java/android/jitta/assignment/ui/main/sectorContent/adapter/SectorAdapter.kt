package android.jitta.assignment.ui.main.sectorContent.adapter

import android.graphics.Typeface
import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Sector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SectorAdapter(
    private val onSectorSelected: (sectorId: String) -> Unit
) : RecyclerView.Adapter<SectorAdapter.ViewHolder>() {

    private var itemList = listOf<Sector>()
    private var currentItemId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_sector_item_view, parent, false)
        return ViewHolder(view, ::onSelectedMarketItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList.getOrNull(position)?.let { item ->
            holder.setIsRecyclable(false)
            holder.bind(item, currentItemId)
        }
    }

    override fun getItemCount() = itemList.size

    private fun onSelectedMarketItem(position: Int) {
        itemList[position].id?.let {
            onSectorSelected.invoke(it)
        }
    }

    fun setItem(itemList: List<Sector>, currentItemId: String) {
        this.currentItemId = currentItemId
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View,
        private val onSelectedItem: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onSelectedItem.invoke(adapterPosition)
            }
        }

        fun bind(item: Sector, currentItemId: String) {
            val label = itemView.findViewById<TextView>(R.id.tv_title_label)
            label.text = item.name
            label.setTypeface(
                label.typeface,
                when (item.id) {
                    currentItemId -> Typeface.BOLD
                    else -> Typeface.NORMAL
                }
            )
        }
    }
}