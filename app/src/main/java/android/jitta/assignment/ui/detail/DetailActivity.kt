package android.jitta.assignment.ui.detail

import android.content.Context
import android.content.Intent
import android.jitta.assignment.R
import android.jitta.assignment.base.DataBindingActivity
import android.jitta.assignment.databinding.ActivityDetailBinding
import android.jitta.assignment.ui.detail.adapter.FactorsAdapter
import android.jitta.assignment.ui.detail.adapter.SignsAdapter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : DataBindingActivity<ActivityDetailBinding, DetailViewModel>() {

    private val itemId by lazy {
        intent.getStringExtra(ARGS_ITEM_ID_KEY).orEmpty()
    }

    private val signAdapter by lazy { SignsAdapter(::onComingSoonToast) }

    private val factorsAdapter by lazy { FactorsAdapter() }

    private val vm: DetailViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_detail

    override fun onViewCreated() {
        setSupportActionBar(binding.toolbar)

        binding.btnFollow.setOnClickListener { onComingSoonToast() }

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }

        binding.rvFactorsGroup.apply {
            adapter = this@DetailActivity.factorsAdapter
            layoutManager = LinearLayoutManager(
                this@DetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.rvSignGroup.apply {
            adapter = this@DetailActivity.signAdapter
            layoutManager = LinearLayoutManager(
                this@DetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.tvReadMore.setOnClickListener {
            binding.layoutDescriptionCollapse.isVisible = false
            binding.tvDescriptionExpand.isVisible = true
        }

        binding.layoutWebsiteLink.setOnClickListener {
            vm.website.value?.let {
                var url = it
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://$url"
                }
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }

        }

        binding.swiperefresh.setOnRefreshListener {
            vm.initialize(itemId)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateViewModel() {
        binding.vm = vm

        vm.typeDiff.observe(this) { type ->
            when (type) {
                "GOOD" -> {
                    binding.tvValueDiffLabel.setTextColor(
                        resources.getColor(
                            R.color.text_green,
                            null
                        )
                    )
                    binding.tvPercentageLabel.setTextColor(
                        resources.getColor(
                            R.color.text_green,
                            null
                        )
                    )
                }

                "BAD" -> {
                    binding.tvValueDiffLabel.setTextColor(
                        resources.getColor(
                            R.color.text_red,
                            null
                        )
                    )
                    binding.tvPercentageLabel.setTextColor(
                        resources.getColor(
                            R.color.text_red,
                            null
                        )
                    )
                }
            }
        }

        vm.factorItem.observe(this) {
            factorsAdapter.setItem(it)
        }

        vm.signListItem.observe(this) {
            signAdapter.setItem(it)
        }

        vm.detailItem.observe(this) {
            // viewState Loading Success
            binding.vfContainer.displayedChild = 1
            binding.swiperefresh.isRefreshing = false
        }

        vm.initialize(itemId)
    }

    private fun onComingSoonToast() {
        Toast.makeText(this, "This feature is available soon", Toast.LENGTH_SHORT).show()
    }


    companion object {

        fun launch(context: Context, bundle: Bundle) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }

        const val ARGS_ITEM_ID_KEY = "ARGS_ITEM_ID_KEY"
    }
}