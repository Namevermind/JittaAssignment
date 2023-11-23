package android.jitta.assignment.ui.detail.adapter

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Detail
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SignsAdapter(
    private val onSelectedItem: () -> Unit
) : RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

    private var itemList = listOf<Detail.Sign>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_jitta_sign_item_view, parent, false)
        return ViewHolder(view) { onSelectedItem.invoke() }
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
        private val onSelectedItem: () -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { onSelectedItem.invoke() }
        }

        fun bind(item: Detail.Sign) {
            val ivDividerView = itemView.findViewById<ImageView>(R.id.iv_divider_item)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title_label)
            val tvDetail = itemView.findViewById<TextView>(R.id.tv_value_label)

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

        }

    }
}