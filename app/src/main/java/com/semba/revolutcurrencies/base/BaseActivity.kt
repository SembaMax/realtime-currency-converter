package com.semba.revolutcurrencies.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.semba.revolutcurrencies.utils.NetworkManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by SeMbA on 2019-07-25.
 */
abstract class BaseActivity <T: ViewDataBinding, V: ViewModel>: DaggerAppCompatActivity()
{
    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V

    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    abstract fun afterInjection()

    /**
     * Called from the activity's sub class to specify the required ViewModel class and to pass the injected Factory
     */
    fun assignViewModel(viewModelFactory: ViewModelProvider.Factory, vmClass: Class<V>): V
    {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        afterInjection()
        super.onCreate(savedInstanceState)
        initBinding()
    }

    /**
     * setup the binding work
     */
    private fun initBinding()
    {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    /**
     * Ask for the permissions on Android versions  >= M
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun performPermissions(permissions: Array<String>, requestCode: Int)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions ,requestCode)
    }

    /**
     * Network connectivity helper
     */
    fun isNetworkConnected() = NetworkManager.isConnected(applicationContext)

    /**
     * Check for the required permissions on Android versions  >= M
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermissions(permission: String): Boolean
    {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Called to hide the soft keyboard
     */
    fun hideKeyboard()
    {
        val view = this.currentFocus
        if (view != null)
        {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}