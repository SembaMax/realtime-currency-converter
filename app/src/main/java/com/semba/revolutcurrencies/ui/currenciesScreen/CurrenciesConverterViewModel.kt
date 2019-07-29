package com.semba.revolutcurrencies.ui.currenciesScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.semba.revolutcurrencies.base.BaseViewModel
import com.semba.revolutcurrencies.data.models.LatestCurrenciesResponse
import com.semba.revolutcurrencies.data.repositories.CurrenciesRepository
import com.semba.revolutcurrencies.ui.currenciesScreen.adapters.OnLoadImagePath
import com.semba.revolutcurrencies.utils.Logger
import com.semba.revolutcurrencies.utils.extensions.removeLastChar
import com.semba.revolutcurrencies.utils.rx.AppSchedulerProvider
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by SeMbA on 2019-07-25.
 */
class CurrenciesConverterViewModel
@Inject constructor(private val currenciesRepository: CurrenciesRepository
                    ,private val mSchedulerProvider: AppSchedulerProvider
                    ,private val logger: Logger): BaseViewModel<ICurrenciesNavigator>() {

    companion object {
        var defaultBase = "EUR"
    }

    var currenciesData = MutableLiveData<LatestCurrenciesResponse>()
    var baseCurrency = MutableLiveData<String>()

    /**
     * Observe the changes of the current selected base currency, in order to notify the api service with the new base currency.
     */
    private val baseCurrencyObserver = Observer<String> {
        mCompositeDisposable.clear()
        fetchLatestCurrencies(it)
    }

    /**
     * Observe latest currencies updates, which is provided by the api service
     */
    private val latestCurrenciesObserver = Observer<LatestCurrenciesResponse> {
        if (it.base != null && it.rates != null)
            mNavigator?.get()?.updateCurrencyRateItems(it.base!!, it.rates!!)
    }

    init {
        // Must unsubscribe these observers with onCleared().
        baseCurrency.observeForever(baseCurrencyObserver)
        currenciesData.observeForever(latestCurrenciesObserver)
    }

    /**
     * Runs the api call of "LatestCurrencies" within a disposal to be fully controlled over time, as it's working repeatedly.
     */
    private fun fetchLatestCurrencies(base: String)
    {
        //Show loading indicator only on the first time.
        if (currenciesData.value == null)
            this.isLoading.value = true

        mNavigator?.get()?.toggleLoading(true)
        mCompositeDisposable.add(currenciesRepository.callGetLatestCurrenciesApi(base)
            .observeOn(mSchedulerProvider.ui())
            .subscribeOn(mSchedulerProvider.io())
            .subscribeWith(object : DisposableObserver<LatestCurrenciesResponse>(){
                override fun onComplete() {
                    handleLoading(false)
                    logger.d("Observer: Completed")
                }

                override fun onNext(t: LatestCurrenciesResponse) {
                    handleLoading(false)
                    currenciesData.value = t
                    logger.d(t.toString())
                }

                override fun onError(e: Throwable) {
                    handleLoading(false)
                    logger.e(e.toString())
                    mNavigator?.get()?.showErrorMessage(e.message)
                }
            }))
    }

    /**
     * Get flag image path for a certain code.
     */
    fun fetchCountryFlag(code: String, onLoadImage: OnLoadImagePath) {
        launch {
            val imagePath = currenciesRepository.callGetFlagApiAsync(code.removeLastChar()).await()
            withContext(Dispatchers.Main) {
                onLoadImage.updateCurrencyImage(code, imagePath.flag)
            }
        }
    }

    /**
     * update isLoading livedata value.
     */
    private fun handleLoading(loading: Boolean) {
        isLoading.value = loading
    }

    /**
     * Unsubscribe all our observers.
     */
    override fun onCleared() {
        currenciesData.removeObserver(latestCurrenciesObserver)
        baseCurrency.removeObserver(baseCurrencyObserver)
        super.onCleared()
    }
}