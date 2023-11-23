package android.jitta.assignment.ui.detail.adapter.table

import android.jitta.assignment.R
import android.jitta.assignment.ui.detail.adapter.table.model.TableContent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ColumnsAdapter : RecyclerView.Adapter<ColumnsAdapter.ViewHolder>() {

    private var itemList = listOf<TableContent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_columns_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList.getOrNull(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: List<TableContent>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        val adapter by lazy { RowsAdapter() }

        fun bind(item: TableContent) {
            val rvRows = itemView.findViewById<RecyclerView>(R.id.rv_rows)

            rvRows.apply {
                adapter = this@ViewHolder.adapter
                layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            adapter.setItem(item.listOfLabel, item.type)

        }
    }

}