package android.jitta.assignment.ui.main.adapter.viewholder

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.RankingItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NormalViewHolder(
    view: View,
    private val onSelectedItem: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            onSelectedItem.invoke(adapterPosition)
        }
    }

    fun bind(item: RankingItem) {
        val title = itemView.findViewById<TextView>(R.id.tv_title_label)
        val id = itemView.findViewById<TextView>(R.id.tv_id_label)
        val rank = itemView.findViewById<TextView>(R.id.tv_rank_label)
        val count = itemView.findViewById<TextView>(R.id.tv_count_label)

        title.text = item.title
        id.text = item.id
        rank.text = item.rank.toString()
        count.text = "/${item.count}"
    }
}