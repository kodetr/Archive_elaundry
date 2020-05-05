package com.kodetr.elaundry.utils

import android.content.Context
import android.content.DialogInterface
import android.app.AlertDialog

object AlertDialogBox {

    fun Alert(ctx: Context, mess: String) {
        val alert = AlertDialog.Builder(ctx)
        alert.setMessage(mess)
        alert.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
        alert.show()
    }
}
