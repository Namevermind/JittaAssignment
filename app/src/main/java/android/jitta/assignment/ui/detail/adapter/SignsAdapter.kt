package android.jitta.assignment.ui.detail.adapter

import android.annotation.SuppressLint
import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Detail
import android.jitta.assignment.ui.detail.adapter.table.ColumnsAdapter
import android.jitta.assignment.ui.detail.adapter.table.model.TableContent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SignsAdapter : RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

    private var itemList = listOf<Detail.Sign>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_jitta_sign_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemList.getOrNull(position)?.let { item ->
            holder.bind(item)
        }
    }

    override fun getItemCount() = itemList.size

    fun setItem(itemList: List<Detail.Sign>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        val adapter by lazy { ColumnsAdapter() }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Detail.Sign) {
            val ivDividerView = itemView.findViewById<ImageView>(R.id.iv_divider_item)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_label)
            val tvDetail = itemView.findViewById<TextView>(R.id.tv_value_label)
            val ivMore = itemView.findViewById<ImageView>(R.id.iv_more_icon)
            val rvColumns = itemView.findViewById<RecyclerView>(R.id.rv_columns)

            when (item.type) {
                "bad" -> ivDividerView.setImageDrawable(
                    itemView.resources.getDrawable(
                        R.drawable.bg_divider_sign_red,
                        null
                    )
                )

                else -> ivDividerView.setImageDrawable(
                    itemView.resources.getDrawable(
                        R.drawable.bg_divider_sign_green,
                        null
                    )
                )
            }

            tvTitle.text = item.title.orEmpty()
            tvDetail.text = item.value.orEmpty()

            rvColumns.apply {
                adapter = this@ViewHolder.adapter
                layoutManager = LinearLayoutManager(
                    itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

            val itemTableContent = item.display.mapToListTableContent()
            adapter.setItem(itemTableContent)

            itemView.setOnClickListener {
                rvColumns.isVisible = !rvColumns.isVisible
                ivMore.setImageDrawable(
                    itemView.resources.getDrawable(
                        when (rvColumns.isVisible) {
                            true -> R.drawable.ic_expand_less
                            false -> R.drawable.ic_expand_more
                        }, null
                    )

                )

            }

        }

        private fun Detail.Sign.Display.mapToListTableContent(): List<TableContent> {
            return mutableListOf<TableContent>().apply {
                val listOfHeads = mutableListOf("")
                this@mapToListTableContent.columnHead?.map { listOfHeads.add(it) }
                add(TableContent.Head(listOfHeads))

                this@mapToListTableContent.columns?.map { columns ->
                    val listOfColumns = mutableListOf(columns.name.orEmpty())
                    columns.data?.map { listOfColumns.add(it) }
                    add(TableContent.Column(listOfColumns))
                }
            }
        }

    }
}