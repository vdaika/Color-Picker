package com.vdk.color.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.*

class ColorCircleView : ColorView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun calculateCursorPosition() {
        val distance: Float = mRect.width() / 2 * mHsv[1] // saturation
        val angle = (mHsv[0] - 180) * Math.PI / 180.0f // hue
        mCursorPosX = (width / 2F - distance * cos(angle)).toFloat()
        mCursorPosY = (height / 2F - distance * sin(angle)).toFloat()

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        var distance = sqrt(
                (event.x.toDouble() - width / 2).pow(2.0) + (event.y.toDouble() - height / 2).pow(2.0)
        )
        if (event.action == MotionEvent.ACTION_DOWN && distance > mRect.width() / 2) return false

        mCursorPosX = event.x
        mCursorPosY = event.y

        if (distance > mRect.width() / 2F) {
            mCursorPosX = (width / 2F + mRect.width() / 2F * (event.x - width / 2F) / distance).toFloat()
            mCursorPosY = (height / 2F + mRect.height() / 2F * (event.y - height / 2F) / distance).toFloat()
        }

        mHsv[0] = (atan2((-mCursorPosY + height / 2.0), (width / 2.0 - mCursorPosX)) * 180.0 / Math.PI + 180).toFloat()
        mHsv[1] = (distance / (mRect.width() / 2.0)).toFloat()
        mOnColorChangedListener?.onColorChanged(Color.HSVToColor(mAlpha, mHsv))

        invalidate()

        return true
    }

    override fun drawContent(canvas: Canvas?) {
        canvas?.drawCircle(width / 2F, height / 2F, mRect.width() / 2F, mHuePaint)
        canvas?.drawCircle(width / 2F, height / 2F, mRect.width() / 2F, mSaturationPaint)
    }

    override fun getOffsetVertical(): Int {
        return mCursorSize / 2;
    }

    override fun initRect(w: Int, h: Int, oldw: Int, oldh: Int) {
        var size = min(w, h) - mCursorSize
        if (size < 0) size = 0
        mRect.set((w - size) / 2, (h - size) / 2, (w + size) / 2, (h + size) / 2)
    }

    override fun refreshColorView() {
        mHuePaint.isAntiAlias = true
        mHuePaint.style = Paint.Style.FILL

        val hsv = floatArrayOf(0f, 60f, 120f, 180f, 240f, 300f, 360f)
        val hueColors = IntArray(7)
        for (i in hueColors.indices) {
            hueColors[i] = Color.HSVToColor(0xFF, floatArrayOf(hsv[i], 1F, 1F))
        }
        val hueGradient = SweepGradient(
                width / 2F,
                height / 2F,
                hueColors,
                null
        )
        mHuePaint.shader = hueGradient

        mSaturationPaint.isAntiAlias = true
        mSaturationPaint.style = Paint.Style.FILL
        val saturationGradient = RadialGradient(
                width / 2F,
                height / 2F,
                mRect.width() / 2F,
                Color.WHITE,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
        )

        mSaturationPaint.shader = saturationGradient
    }
}