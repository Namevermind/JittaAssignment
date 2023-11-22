package android.jitta.assignment.ui.main.adapter

import android.jitta.assignment.data.entities.RankingItem
import androidx.recyclerview.widget.DiffUtil

class ItemRankingDiffCallback(
    private val oldItems: List<RankingItem>,
    private val newItems: List<RankingItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.getOrNull(oldItemPosition) === newItems.getOrNull(newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.getOrNull(oldItemPosition) == newItems.getOrNull(newItemPosition)
    }
}