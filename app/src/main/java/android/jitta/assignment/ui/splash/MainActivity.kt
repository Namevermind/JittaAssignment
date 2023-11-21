package android.jitta.assignment.ui.splash

import android.jitta.assignment.R
import android.jitta.assignment.base.DataBindingActivity
import android.jitta.assignment.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DataBindingActivity<ActivityMainBinding, MainViewModel>() {

    private val vm: MainViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreateViewModel() {
        binding.vm = vm

        vm.countryList.observe(this) {
            val a = it
            val b = a
            binding.tvLabel.text = "size = ${it?.size}"
        }

        vm.initialize()
    }
}