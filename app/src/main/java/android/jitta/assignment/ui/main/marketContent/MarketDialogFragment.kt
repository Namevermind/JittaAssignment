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
    private val itemList: List<Country> = mockItem,
    private val currentItemId: String = "T1",
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
        const val TAG = "MarketBottomSheet"

        val mockItem = listOf(
            Country("T1", "test1", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T2", "test2", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T3", "test3", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T4", "test4", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T5", "test5", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T6", "test6", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T7", "test7", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T8", "test8", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T9", "test9", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T10", "test10", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T11", "test11", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T12", "test12", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T13", "test13", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T14", "test14", "\uD83C\uDDE6\uD83C\uDDFA"),
            Country("T15", "test15", "\uD83C\uDDE6\uD83C\uDDFA"),

            )
    }
}