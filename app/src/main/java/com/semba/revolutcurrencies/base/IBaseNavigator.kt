package com.semba.revolutcurrencies.base

/**
 * Created by SeMbA on 2019-07-25.
 */
interface IBaseNavigator {

    /**
     * Switch between loading mode and normal mode
     */
    fun toggleLoading(isLoading: Boolean)

    /**
     * pass a error message to the activity to display it on the user interface
     */
    fun showErrorMessage(message: String?)
}