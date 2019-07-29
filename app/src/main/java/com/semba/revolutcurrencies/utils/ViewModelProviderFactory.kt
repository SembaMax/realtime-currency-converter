package com.semba.revolutcurrencies.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


/**
 * Created by SeMbA on 2019-07-25.
 */

/**
 * The class works as a viewModel factory for the overall screens inside the app.
 * it is been injected by Dagger in all the target ui components.
 * Notes: Dagger is using MultiBinding to inject the viewModels along with their Classes to this Factory class.
 * and using Kotlin demands to add the annotation "@JvmSuppressWildcards" to allow the convenient integration with the generated Java code in MultiBinding.
 */
@Singleton
class ViewModelProviderFactory @Inject constructor(private val creators: Map< Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel> >) : ViewModelProvider.NewInstanceFactory() {

    private val TAG = "REVOLUT_VIEW_MODEL_FACTORY"

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) { // ViewModel has not been created

            // Check if the Class is represented by any Key in the map.
            for ((key, value) in creators) {

                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        // throw exception (No ViewModel)
        if (creator == null) {
            throw IllegalArgumentException("$TAG : unknown model class $modelClass")
        }

        // Provide the ViewModel
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}