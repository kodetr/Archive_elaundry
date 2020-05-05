package com.kodetr.elaundry.model

class Services(
    var serviceName: String?,
    var servicePrice: Int,
    var serviceAmount: Int,
    var serviceImage: Int,
    var serviceType: String?
) {
    var serviceRemain: Int = 0


    init {
        this.serviceRemain = serviceAmount
    }

    override fun hashCode(): Int {
        return this.serviceName!!.hashCode() + this.servicePrice
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj === this) true else obj is Services &&
                this.serviceName == obj.serviceName &&
                this.servicePrice == obj.servicePrice &&
                this.serviceAmount == obj.serviceAmount &&
                this.serviceImage == obj.serviceImage &&
                this.serviceType == obj.serviceType &&
                this.serviceRemain == obj.serviceRemain

    }
}
