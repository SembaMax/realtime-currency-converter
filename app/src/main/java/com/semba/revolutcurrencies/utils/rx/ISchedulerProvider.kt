package com.semba.revolutcurrencies.utils.rx

import io.reactivex.Scheduler

/**
 * Created by SeMbA on 2019-07-26.
 */
interface ISchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun newThread(): Scheduler

    fun single(): Scheduler
}