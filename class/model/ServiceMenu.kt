package com.kodetr.elaundry.model

import java.util.ArrayList

class ServiceMenu {
    var menuName: String? = null
    var menuImage: Int = 0
    var servicesList: ArrayList<Services>? = null

    constructor(menuName: String, menuImage: Int, dishList: ArrayList<Services>?) {
        this.menuName = menuName
        this.menuImage = menuImage
        this.servicesList = dishList
    }
}
