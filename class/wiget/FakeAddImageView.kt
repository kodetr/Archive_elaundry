package com.kodetr.elaundry.wiget

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.PointF
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class FakeAddImageView : ImageView {
    private val mPointF: PointF? = null

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun setMPointF(pointF: PointF) {
        x = pointF.x
        y = pointF.y
    }

    fun getmPointF(): PointF? {
        return mPointF
    }

    fun setmPointF(mPointF: PointF) {
        x = mPointF.x
        y = mPointF.y
    }
}
