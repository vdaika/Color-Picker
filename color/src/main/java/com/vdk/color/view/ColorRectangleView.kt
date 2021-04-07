package com.vdk.color.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent


class ColorRectangleView : ColorView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun calculateCursorPosition() {
        mCursorPosX = mRect.left + mRect.width() * (mHsv[0] / 360F)
        mCursorPosY = mRect.top + mRect.height() * mHsv[1]

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN && !mRect.contains(event.x.toInt(), event.y.toInt())) return false

        mCursorPosX = event.x
        mCursorPosY = event.y

        if (mCursorPosX < mRect.left) mCursorPosX = mRect.left.toFloat()
        if (mCursorPosX > mRect.right) mCursorPosX = mRect.right.toFloat()
        if (mCursorPosY < mRect.top) mCursorPosY = mRect.top.toFloat()
        if (mCursorPosY > mRect.bottom) mCursorPosY = mRect.bottom.toFloat()

        mHsv[0] = (mCursorPosX - mRect.left) * 360F / mRect.width()
        mHsv[1] = (mCursorPosY - mRect.top) / mRect.height()
        mOnColorChangedListener?.onColorChanged(Color.HSVToColor(mAlpha, mHsv))

        invalidate()

        return true
    }

    override fun drawContent(canvas: Canvas?) {
        canvas?.drawRect(mRect, mHuePaint)
        canvas?.drawRect(mRect, mSaturationPaint)
    }

    override fun getOffsetVertical(): Int {
        return mCursorSize / 2;
    }

    override fun refreshColorView() {
        mHuePaint.isAntiAlias = true
        mHuePaint.style = Paint.Style.FILL

        val hsv = floatArrayOf(0f, 60f, 120f, 180f, 240f, 300f, 360f)
        val hueColors = IntArray(7)
        for (i in hueColors.indices) {
            hueColors[i] = Color.HSVToColor(0xFF, floatArrayOf(hsv[i], 1F, 1F))
        }
        val hueGradient = LinearGradient(
                mRect.left.toFloat(),
                mRect.top.toFloat(),
                mRect.right.toFloat(),
                mRect.top.toFloat(),
                hueColors,
                null,
                Shader.TileMode.CLAMP
        )
        mHuePaint.shader = hueGradient

        mSaturationPaint.isAntiAlias = true
        mSaturationPaint.style = Paint.Style.FILL
        val saturationGradient = LinearGradient(
                mRect.left.toFloat(),
                mRect.top.toFloat(),
                mRect.left.toFloat(),
                mRect.bottom.toFloat(),
                Color.WHITE,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
        )

        mSaturationPaint.shader = saturationGradient
    }
}