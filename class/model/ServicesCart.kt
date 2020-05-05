package com.kodetr.elaundry.model

import android.util.Log

import java.util.HashMap

class ServicesCart {
    var servicesAccount: Int = 0
        private set
    var servicesTotalPrice: Int = 0
        private set
    private val servicesSingle: MutableMap<Services, Int>

    val servicesSingleMap: Map<Services, Int>
        get() = servicesSingle

    val serviceAccount: Int
        get() = servicesSingle.size

    init {
        this.servicesAccount = 0
        this.servicesTotalPrice = 0
        this.servicesSingle = HashMap()
    }

    fun addServicesSingle(services: Services): Boolean {
        var remain = services.serviceRemain
        if (remain <= 0)
            return false
        services.serviceRemain = --remain
        var num = 0
        if (servicesSingle.containsKey(services)) {
            num = servicesSingle[services]!!
        }
        num += 1
        servicesSingle[services] = num
        Log.e("TAG", "addservicesSingle: " + servicesSingle[services]!!)

        servicesTotalPrice += services.servicePrice
        servicesAccount++
        return true
    }

    fun subServicesSingle(services: Services): Boolean {
        var num = 0
        if (servicesSingle.containsKey(services)) {
            num = servicesSingle[services]!!
        }
        if (num <= 0) return false
        num--
        var remain = services.serviceRemain
        services.serviceRemain = ++remain
        servicesSingle[services] = num
        if (num == 0) servicesSingle.remove(services)

        servicesTotalPrice -= services.servicePrice
        servicesAccount--
        return true
    }

    fun clear() {
        this.servicesAccount = 0
        this.servicesTotalPrice = 0
        this.servicesSingle.clear()
    }
}
