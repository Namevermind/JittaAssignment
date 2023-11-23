package android.jitta.assignment.ui.main.adapter

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.RankingItem
import android.jitta.assignment.ui.main.adapter.viewholder.LoadingViewHolder
import android.jitta.assignment.ui.main.adapter.viewholder.NormalViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(
    private val onSelectedItem: (selectItemId: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList = listOf<RankingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TYPE_NORMAL -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.layout_ranking_item_view, parent, false)
                NormalViewHolder(view, ::onSelectedItem)
            }

            ViewType.TYPE_LOADING -> {
                val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.layout_loading_item_view, parent, false)
                LoadingViewHolder(view)
            }

            else -> throw NullPointerException("View Type $viewType doesn't match with any existing order detail type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalViewHolder) {
            itemList.getOrNull(position)?.let { item ->
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].id.isNullOrEmpty()) {
            ViewType.TYPE_LOADING
        } else {
            ViewType.TYPE_NORMAL
        }
    }

    fun setItem(itemList: List<RankingItem>, isHasMoreItems: Boolean) {
        val newItems = mutableListOf<RankingItem>()
        newItems.addAll(itemList)
        if (isHasMoreItems && itemList.isNotEmpty()) {
            newItems.add(RankingItem.emptyItem)
        }
        val oldItem = this.itemList
        this.itemList = newItems

        DiffUtil.calculateDiff(ItemRankingDiffCallback(oldItem, newItems))
            .dispatchUpdatesTo(this)
    }

    private fun onSelectedItem(position: Int) {
        itemList[position].id?.let {
            onSelectedItem.invoke(it)
        }
    }

    object ViewType {
        const val TYPE_NORMAL = 1
        const val TYPE_LOADING = 2
    }
}