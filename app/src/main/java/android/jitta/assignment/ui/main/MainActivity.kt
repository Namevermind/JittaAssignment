package android.jitta.assignment.ui.main

import android.graphics.Color
import android.jitta.assignment.R
import android.jitta.assignment.base.DataBindingActivity
import android.jitta.assignment.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DataBindingActivity<ActivityMainBinding, MainViewModel>(),
    AppBarLayout.OnOffsetChangedListener {

    private val vm: MainViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_main

    override fun onViewCreated() {
        binding.appBar.addOnOffsetChangedListener(this)

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

    override fun onCreateViewModel() {
        binding.vm = vm

        vm.onLoadingInitSuccess.observe(this) {
//            binding.vfContainer.displayedChild = 1
        }

        vm.rankingList.observe(this) { rankingList ->

        }

        vm.initialize()
    }




}