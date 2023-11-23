package android.jitta.assignment.domain.di

import android.jitta.assignment.domain.GetCountryListUseCase
import android.jitta.assignment.domain.GetDetailByIdUseCase
import android.jitta.assignment.domain.GetRankingListUseCase
import android.jitta.assignment.domain.GetSectorListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCountryListUseCase(get()) }
    factory { GetSectorListUseCase(get()) }
    factory { GetRankingListUseCase(get()) }
    factory { GetDetailByIdUseCase(get()) }
}