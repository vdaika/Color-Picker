package com.vdk.color.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import com.vdknd.color.color.colordemo.color.R

// TODO make transparency
class ColorOpacityView : ColorView {

    private lateinit var mGradientDrawable: GradientDrawable
    private var mGradientColors = intArrayOf(Color.TRANSPARENT, Color.WHITE)

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun initView() {
        super.initView()
        mGradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mGradientColors)
    }

    override fun updateValue() {
        mGradientColors[1] = Color.HSVToColor(floatArrayOf(mHsv[0], mHsv[1], 1F))
        mGradientDrawable.colors = mGradientColors
    }

    override fun calculateCursorPosition() {
        mCursorPosY = mRect.height() / 2F
        mCursorPosX = (mRect.left + (mAlpha / 0xFF) * mRect.width()).toFloat()
        invalidate()
    }

    override fun drawContent(canvas: Canvas?) {
        if (canvas != null) {
            mGradientDrawable.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN && !mRect.contains(event.x.toInt(), event.y.toInt())) return false

        mCursorPosX = event.x

        if (mCursorPosX < mRect.left) mCursorPosX = mRect.left.toFloat()
        if (mCursorPosX > mRect.right) mCursorPosX = mRect.right.toFloat()

        mAlpha = (0xFF * (mCursorPosX - mRect.left) / mRect.width()).toInt()
        mOnColorChangedListener?.onColorChanged(Color.HSVToColor(mAlpha, mHsv))

        invalidate()

        return true
    }

    override fun refreshColorView() {
        mGradientDrawable.bounds = mRect
        mGradientDrawable.cornerRadius = width / 2F
    }
}