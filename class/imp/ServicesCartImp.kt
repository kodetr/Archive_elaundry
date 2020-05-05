package com.kodetr.elaundry.imp

import android.view.View

interface ServicesCartImp {
    fun add(view: View, postion: Int)
    fun remove(view: View, postion: Int)
}
