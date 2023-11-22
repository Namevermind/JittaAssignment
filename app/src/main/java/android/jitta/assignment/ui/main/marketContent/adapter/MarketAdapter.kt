package android.jitta.assignment.ui.main.marketContent.adapter

import android.graphics.Typeface
import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Country
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MarketAdapter(
    private val onMarketSelected: (marketCode: String) -> Unit
) : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    private var itemList = listOf<Country>()
    private var currentItemId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_market_item_view, parent, false)
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
        val marketCode = itemList[position].code
        onMarketSelected.invoke(marketCode)
    }

    fun setItem(itemList: List<Country>, currentItemId: String) {
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

        fun bind(item: Country, currentItemId: String) {
            val flag = itemView.findViewById<TextView>(R.id.tv_flag_label)
            val label = itemView.findViewById<TextView>(R.id.tv_title_label)

            flag.text = item.flag
            label.text = item.name

            label.setTypeface(
                label.typeface,
                when (item.code) {
                    currentItemId -> Typeface.BOLD
                    else -> Typeface.NORMAL
                }
            )
        }

    }
}