package android.jitta.assignment.ui.detail.adapter.table

import android.jitta.assignment.R
import android.jitta.assignment.ui.detail.adapter.table.model.TableContent.DisplayType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RowsAdapter : RecyclerView.Adapter<RowsAdapter.ViewHolder>() {

    private lateinit var displayType: DisplayType
    private var itemList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            DisplayType.COLUMN_HEAD.viewType -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_columns_head_view, parent, false)
                ViewHolder(view)
            }

            DisplayType.COLUMN.viewType -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_columns_view, parent, false)
                ViewHolder(view)
            }

            else -> throw NullPointerException("View Type $viewType doesn't match with any existing order detail type")
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList.getOrNull(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return displayType.viewType
    }

    fun setItem(itemList: List<String>, displayType: DisplayType) {
        this.displayType = displayType
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: String) {
            val root = itemView.findViewById<LinearLayout>(R.id.root)
            val tvLabel = itemView.findViewById<TextView>(R.id.tv_item_label)

            if (adapterPosition == 0) {
                root.setBackgroundColor(itemView.resources.getColor(R.color.gray_sky, null))
            }
            tvLabel.text = item
        }
    }
}