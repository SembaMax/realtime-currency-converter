package com.semba.revolutcurrencies.base

import android.content.Context
import androidx.multidex.MultiDex
import com.semba.revolutcurrencies.di.component.AppComponent
import com.semba.revolutcurrencies.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by SeMbA on 2019-07-25.
 */
class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Setup multidex.
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * Building dagger injection.
     */
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}