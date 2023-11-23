package android.jitta.assignment.ui.detail.adapter

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Detail
import android.jitta.assignment.type.FactorLevel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator

class FactorsAdapter : RecyclerView.Adapter<FactorsAdapter.ViewHolder>() {

    private var itemList = listOf<Detail.Factor.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_jitta_factor_item_view, parent, false)
        return FactorsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList.getOrNull(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount() = itemList.size

    fun setItem(item: Detail.Factor?) {
        val itemList = mutableListOf<Detail.Factor.Item>()

        item?.apply {
            growthItem?.let { itemList.add(it) }
            recentItem?.let { itemList.add(it) }
            financialItem?.let { itemList.add(it) }
            returnItem?.let { itemList.add(it) }
            managementItem?.let { itemList.add(it) }
        }

        this.itemList = itemList
        notifyDataSetChanged()
    }


    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Detail.Factor.Item) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_label)
            val tvValue = itemView.findViewById<TextView>(R.id.tv_value_label)
            val progressLevel = itemView.findViewById<LinearProgressIndicator>(R.id.lpi_level)

            tvTitle.text = item.name.orEmpty()
            tvValue.text = item.value?.let { String.format("%.0f", it) } ?: "0.0"

            val color = when (item.level) {
                FactorLevel.HIGH -> R.color.text_green
                FactorLevel.MEDIUM -> R.color.primary
                FactorLevel.LOW -> R.color.red
                else -> R.color.primary
            }

            progressLevel.apply {
                progress = item.value?.toInt() ?: 0
                setIndicatorColor(itemView.resources.getColor(color, null))
            }

        }
    }
}