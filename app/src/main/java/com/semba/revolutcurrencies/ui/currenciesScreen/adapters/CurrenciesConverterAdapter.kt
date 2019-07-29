package com.semba.revolutcurrencies.ui.currenciesScreen.adapters

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.jakewharton.rxbinding3.widget.textChanges
import com.semba.revolutcurrencies.R
import com.semba.revolutcurrencies.base.BaseViewHolder
import com.semba.revolutcurrencies.data.models.CurrencyRate
import com.semba.revolutcurrencies.ui.currenciesScreen.ICurrenciesNavigator
import com.semba.revolutcurrencies.utils.extensions.roundedString
import com.semba.revolutcurrencies.utils.extensions.toSafeDouble
import kotlinx.android.synthetic.main.currency_converter_item.view.*

/**
 * Created by SeMbA on 2019-07-26.
 */
class CurrenciesConverterAdapter(private val parentActivityContract: ICurrenciesNavigator, private val glide: RequestManager): RecyclerView.Adapter<BaseViewHolder>(){

    /**
     * Items: is the data source field for the recyclerView adapter. it contains updated version of the latest currencies rates.
     */
    private var items: ArrayList<CurrencyRate>? = null

    /**
     * this field holds the current entered amount of Money the it would be transferred to other currencies.
     */
    private var currentMoneyAmount: Double = 0.0

    /**
     * Holds the current currency base the the whole calculations are done around it.
     */
    private var currentBase = MutableLiveData<String>()

    init {
        currentBase.observe(parentActivityContract as LifecycleOwner, Observer {
            parentActivityContract.notifyWithNewBase(it)
        })
    }

    /**
     * This listener is a callback of the Flag API service, so it fires with every response for all the flags images,
     * After calling "parentActivityContract.requestImage()" as this call runs a coroutine of the api request in the ViewModel.
     * Note: This is not used anymore, I found a more efficient way to get flags' images which is implemented in "CurrencyRate.getImagePath()"
     * If you want to use it, then you have to add this line ".also { it.forEach { parentActivityContract.requestImage(it.code, imageUpdater) } }"
     * to the initial condition in "updateItems" method.
     */
    private val imageUpdater = object : OnLoadImagePath {
        override fun updateCurrencyImage(code: String, imagePath: String) {
            val position = items?.indexOfFirst { it.code == code } ?: return

            //items?.get(position)?.imagePath = imagePath
            notifyItemChanged(position, imagePath)
            Log.e("Show_Amount", "Code $code  &  flag $imagePath")
        }
    }

    /**
     * Here the initialization of the first Base item.
     */
    private fun setInitialBase(base: String) {
        if (items?.any { it.code == base } == false)
        {
            items?.add(0,CurrencyRate(base, 0.0))
        }
        updateCurrentBase(base)
    }

    /**
     * Update the list items in result of observing on the currencies livedata object in ViewModel.
     * gets called from the parent activity.
     */
    fun updateItems(base: String, ratesMap: Map<String, Double>)
    {
        if (items == null)
            items = ArrayList(ratesMap.map { CurrencyRate(it.key, it.value) })
        else
            items?.forEach { it.rate = ratesMap[it.code] }

        if (currentBase.value == null)
            setInitialBase(base)

        notifyItemRangeChanged(1, itemCount, currentMoneyAmount)
    }

    /**
     * Set value of "currentBase" livedata object.
     * gets called on changing the current Base currency from the list items.
     */
    fun updateCurrentBase(newBase: String)
    {
        currentBase.value = newBase
    }

    /**
     * Returns a copy of CurrencyRate object, to safely manipulate it.
     */
    private fun getCopyOfItemAtPosition(position: Int): CurrencyRate
    {
        return items?.get(position)?.copy() ?: CurrencyRate.EMPTY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_converter_item, parent, false)
        return CurrenciesConverterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    /**
     * Adapter ViewHolder
     */
    inner class CurrenciesConverterViewHolder(itemView: View): BaseViewHolder(itemView)
    {
        private val codeTextView: TextView = itemView.currency_code_tv
        private val amountEditText: EditText = itemView.currency_amount_et
        private val flagImageView: ImageView = itemView.currency_flag_image

        override fun onBind(position: Int) {
            val rate = getCopyOfItemAtPosition(position)
            renderItemView(rate)
            handleFocus(rate, position)
            handleAmountObserver()
        }

        /**
         * Setup the view components and data
         */
        private fun renderItemView(rate: CurrencyRate) {

            codeTextView.text = rate.code
            glide.load(rate.getImagePath()).into(flagImageView)

            if (rate.code != currentBase.value)
            {
                amountEditText.setText(((rate.rate ?: 0.0) * currentMoneyAmount).roundedString())
            }
            else
            {
                amountEditText.setText(currentMoneyAmount.toString())
            }
        }

        /**
         * Sets focus listener on the amount editText to keep the selected base currency item always on the top of the list.
         */
        private fun handleFocus(rate: CurrencyRate, position: Int) {
            amountEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    //Remove the RxBinding observer. but unfortunately it didn't work, RxBinding has no unsubscribe method.
                    //apply { (v as EditText).textChanges().subscribe(observer = null) }
                    return@setOnFocusChangeListener
                }

                if (position != 0)
                {
                    //Swap the focused item to the first position
                    items?.removeAt(position).also {
                        items?.add(0,rate)
                    }

                    //Notify the adapter to visualize the swap
                    Handler().post {
                        notifyItemMoved(position, 0)
                    }

                    //Notify the Activity with the new selected base
                    updateCurrentBase(rate.code ?: "")
                }
            }
        }

        /**
         * Subscribe RxBinding observer on the amount editText to notify with its changes, in order to update the other currencies.
         */
        private fun handleAmountObserver() {
            //Subscribe a RxBinding observer to watch the text changes
            apply {
                amountEditText.textChanges().subscribe {
                    //Update the current amount
                    if (amountEditText.isFocused)
                        currentMoneyAmount = it.toString().toSafeDouble()
                }
            }
        }
    }

}