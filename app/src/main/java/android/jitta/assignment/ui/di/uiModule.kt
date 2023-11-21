package android.jitta.assignment.ui.di

import android.jitta.assignment.ui.splash.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainViewModel(get(), get()) }
}