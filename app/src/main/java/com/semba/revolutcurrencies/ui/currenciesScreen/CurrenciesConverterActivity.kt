package com.semba.revolutcurrencies.ui.currenciesScreen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.semba.revolutcurrencies.BR
import com.semba.revolutcurrencies.R
import com.semba.revolutcurrencies.base.BaseActivity
import com.semba.revolutcurrencies.ui.currenciesScreen.adapters.CurrenciesConverterAdapter
import kotlinx.android.synthetic.main.activity_currencies_converter.*
import javax.inject.Inject
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.semba.revolutcurrencies.databinding.ActivityCurrenciesConverterBinding
import com.semba.revolutcurrencies.ui.currenciesScreen.adapters.OnLoadImagePath


class CurrenciesConverterActivity : BaseActivity<ActivityCurrenciesConverterBinding, CurrenciesConverterViewModel>(), ICurrenciesNavigator {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    @Inject
    lateinit var requestManager: RequestManager

    var adapter: CurrenciesConverterAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_currencies_converter
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    /**
     * After the injection is done, we need to generate our viewModel which is based on the injected "viewModelFactory".
     * Also we define the navigator of the generated viewModel.
     */
    override fun afterInjection() {
        this.assignViewModel(viewModelFactory, CurrenciesConverterViewModel::class.java)
        this.mViewModel.setNavigator(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCurrenciesRecyclerView()
        launchInitialApiCall()
    }

    private fun launchInitialApiCall() {
        //Fires the initial api call with the default base currency.
        notifyWithNewBase(CurrenciesConverterViewModel.defaultBase)
    }

    /**
     * setup the recyclerView options and the adapter.
     */
    private fun initCurrenciesRecyclerView() {
        adapter = CurrenciesConverterAdapter(this, requestManager)
        currencies_recyclerView.layoutManager = LinearLayoutManager(this)
        currencies_recyclerView.adapter = adapter

        currencies_recyclerView.setOnTouchListener { _, _ ->
            this.hideKeyboard()
            false
        }
    }
    
    override fun toggleLoading(isLoading: Boolean) {
        /** Constraint's layout states */
        //currencies_main_layout.setState(if (isLoading) R.id.loading else R.id.normal , currencies_main_layout.width, currencies_main_layout.height)
        currencies_progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun showErrorMessage(message: String?) {
        val defaultMessage = if (isNetworkConnected()) getString(R.string.something_went_wrong) else getString(R.string.check_connection)
        Snackbar.make(currencies_main_layout, message ?: defaultMessage, 2000).show()
    }

    override fun notifyWithNewBase(newBase: String) {
        if (mViewModel.baseCurrency.value != newBase)
            mViewModel.baseCurrency.value = newBase
    }

    override fun updateCurrencyRateItems(base: String, ratesMap: Map<String, Double>) {
        //Notify the adapter with the recent updated version of the currencies data.
        adapter?.updateItems(base, ratesMap)
    }

    override fun requestImage(code: String, onLoadListener: OnLoadImagePath) {
        //This is not used any more.
        mViewModel.fetchCountryFlag(code, onLoadListener)
    }
}
