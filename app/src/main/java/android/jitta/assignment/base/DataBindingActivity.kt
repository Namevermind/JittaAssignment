package android.jitta.assignment.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

abstract class DataBindingActivity<Binding : ViewDataBinding, VM : ViewModel> : AppCompatActivity(),
    LifecycleOwner {

    protected lateinit var binding: Binding

    protected abstract fun getLayoutResId(): Int

    protected abstract fun onViewCreated()

    protected abstract fun onCreateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.lifecycleOwner = this
        onViewCreated()
        onCreateViewModel()
    }

}