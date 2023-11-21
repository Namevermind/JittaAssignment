package android.jitta.assignment.data.di

import android.jitta.assignment.data.MainRepository
import android.jitta.assignment.data.MainRepositoryImpl
import android.jitta.assignment.data.network.ApiGateway
import android.jitta.assignment.data.network.ApiGatewayImpl
import org.koin.dsl.module

val dataModule = module {
    single { OkHttpBuilder().build() }
    single { ApolloBuilder(get()).build() }

    /** Gateway **/
    single<ApiGateway> { ApiGatewayImpl(get()) }
    /** Repository **/
    single<MainRepository> { MainRepositoryImpl(get()) }
}