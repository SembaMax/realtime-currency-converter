package com.semba.revolutcurrencies.di.component

import android.app.Application
import com.semba.revolutcurrencies.base.BaseApplication
import com.semba.revolutcurrencies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by SeMbA on 2019-07-27.
 */

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    DataModule::class,
    NetworkModule::class,
    UtilsModule::class,
    ActivityBindingModule::class,
    GlideModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<BaseApplication>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}