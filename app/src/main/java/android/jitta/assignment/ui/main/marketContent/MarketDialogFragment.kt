package android.jitta.assignment.ui.main.marketContent

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.ui.main.marketContent.adapter.MarketAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MarketDialogFragment(
    private val itemList: List<Country>,
    private val currentItemId: String,
    private val onMarketSelected: (marketCode: String) -> Unit
) : DialogFragment() {


    private val adapter by lazy { MarketAdapter(::onMarketSelected) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_market_content, container, false)
    }

    override fun getTheme() = R.style.Theme_JittaAssignment_DialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvMarketContent = view.findViewById<RecyclerView>(R.id.rv_market_content)
        val btnClose = view.findViewById<ImageView>(R.id.btn_close_market_dialog)

        btnClose.setOnClickListener {
            this.dismiss()
        }

        rvMarketContent.apply {
            adapter = this@MarketDialogFragment.adapter
            layoutManager = LinearLayoutManager(
                this@MarketDialogFragment.requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        adapter.setItem(itemList, currentItemId)

        rvMarketContent.smoothScrollToPosition(itemList.indexOfFirst { it.code == currentItemId })
    }

    private fun onMarketSelected(marketCode: String) {
        onMarketSelected.invoke(marketCode)
        this.dismiss()
    }

    companion object {
        const val TAG = "MarketDialogFragment"
    }
}