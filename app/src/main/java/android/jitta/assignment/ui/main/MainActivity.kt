package android.jitta.assignment.ui.main

import android.graphics.Color
import android.jitta.assignment.R
import android.jitta.assignment.base.DataBindingActivity
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.databinding.ActivityMainBinding
import android.jitta.assignment.ui.detail.DetailActivity
import android.jitta.assignment.ui.main.adapter.RankingAdapter
import android.jitta.assignment.ui.main.marketContent.MarketDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DataBindingActivity<ActivityMainBinding, MainViewModel>(),
    AppBarLayout.OnOffsetChangedListener {

    private val adapter by lazy { RankingAdapter(::onSelectedItem) }

    private val vm: MainViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_main

    override fun onViewCreated() {
        binding.appBar.addOnOffsetChangedListener(this)
        binding.rvRanking.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            addOnScrollListener(onScrollListener())
        }

        binding.btnMarketFilter.setOnClickListener {
            openDialogMarketFilter(
                itemList = vm.countryList.value.orEmpty(),
                currentItemId = vm.currentMarketType
            )
        }
    }

    override fun onCreateViewModel() {
        binding.vm = vm

        vm.onLoadingInitSuccess.observe(this) {
//            binding.vfContainer.displayedChild = 1
        }

        vm.rankingList.observe(this) { rankingList ->
            val isNewState = adapter.itemCount <= 1
            adapter.setItem(rankingList, vm.isHasMoreRankingItem)

            if (isNewState) {
                binding.rvRanking.scrollToPosition(0)
            }
        }

        vm.initialize()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val offset = verticalOffset * -1
        val progress = offset / appBarLayout.totalScrollRange.toFloat()

        if (progress.isNaN()) {
            return
        }

        val alpha = ((0 + progress) * 255).toInt()
        binding.tvToolbarTitle.setTextColor(Color.argb(alpha, 0, 0, 0))

        val alphaDivider = ((0 + progress) * 30).toInt()
        binding.lineDivider.setBackgroundColor(Color.argb(alphaDivider, 90, 110, 123))
    }

    private fun onScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                vm.loadMoreRankingList()
            }
        }
    }

    private fun openDialogMarketFilter(
        itemList: List<Country>,
        currentItemId: String,
    ) {
        val marketDialogFragment = MarketDialogFragment(
            itemList = itemList,
            currentItemId = currentItemId,
            onMarketSelected = ::onMarketCountryChange
        )
        marketDialogFragment.show(supportFragmentManager, MarketDialogFragment.TAG)
    }

    private fun onMarketCountryChange(marketCode: String) {
        vm.changeMarketRankingList(marketCode)
    }

    private fun onSelectedItem(itemId: String) {
        DetailActivity.launch(context = this)
    }

}