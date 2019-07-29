package com.semba.revolutcurrencies.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext

/**
 * Created by SeMbA on 2019-07-25.
 */
abstract class BaseViewModel<N>: ViewModel(), CoroutineScope {

    /**
     * initialize our coroutine job.
     */
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Handles all the disposables objects that getting around the viewModel
     */
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * This navigator handles the communication between the viewModel and its activity.
     */
    var mNavigator: WeakReference<N>? = null

    /**
     * Observe on the value of isLoading to notify the activity.
     */
    private val loadingObserver = Observer<Boolean> {
        (mNavigator?.get() as IBaseNavigator?)?.toggleLoading(it)
    }

    init {
        isLoading.value = false
        isLoading.observeForever(loadingObserver)
    }

    /**
     * assign the desired navigator after initializing the viewModel.
     */
    fun setNavigator(navigator: N)
    {
        mNavigator = WeakReference(navigator)
    }

    /**
     * Dispose disposables
     * Clear observers
     * Stop coroutines
     */
    override fun onCleared() {
        isLoading.removeObserver(loadingObserver)
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
        job.cancel()
        super.onCleared()
    }

}