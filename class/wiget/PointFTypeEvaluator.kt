package com.kodetr.elaundry.wiget

import android.animation.TypeEvaluator
import android.graphics.PointF

class PointFTypeEvaluator(private val control: PointF) : TypeEvaluator<PointF> {
    private val mPointF = PointF()

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        return getBezierPoint(startValue, endValue, control, fraction)
    }

    private fun getBezierPoint(start: PointF, end: PointF, control: PointF, t: Float): PointF {
        mPointF.x = (1 - t) * (1 - t) * start.x + 2f * t * (1 - t) * control.x + t * t * end.x
        mPointF.y = (1 - t) * (1 - t) * start.y + 2f * t * (1 - t) * control.y + t * t * end.y
        return mPointF
    }
}
