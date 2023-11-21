package android.jitta.assignment

import android.app.Application
import android.jitta.assignment.data.di.dataModule
import android.jitta.assignment.domain.di.domainModule
import android.jitta.assignment.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    uiModule,
                    domainModule,
                    dataModule
                )
            )
        }
    }
}