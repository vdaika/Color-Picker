package com.vdk.color.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.vdk.color.OnColorChangedListener
import com.vdknd.color.color.colordemo.color.R

abstract class ColorView : View {

    private val mCursorPaint: Paint = Paint()
    protected val mHuePaint: Paint by lazy { Paint() }
    protected val mSaturationPaint: Paint by lazy { Paint() }

    protected var mRect: Rect = Rect()
    protected var mCursorPosX = 0.0f
    protected var mCursorPosY = 0.0f
    protected var mCursorSize: Int = 0
    private var mCursorOffset: Int = 0

    protected var mHsv = FloatArray(3)
    protected var mAlpha = 0xFF

    protected var mOnColorChangedListener: OnColorChangedListener? = null

    fun setOnColorChangedListener(listener: OnColorChangedListener) {
        mOnColorChangedListener = listener
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        initView()
    }

    open fun initView() {
        initCursor()
    }

    private fun initCursor() {
        mCursorSize = context.resources.getDimensionPixelSize(R.dimen.color_picker_cursor_size)
        mCursorOffset = context.resources.getDimensionPixelSize(R.dimen.color_picker_cursor_offset)
        mCursorPaint.style = Paint.Style.STROKE
        mCursorPaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.color_picker_cursor_stroke_size).toFloat()
        mCursorPaint.color = Color.WHITE
        mCursorPaint.isAntiAlias = true
    }

    open fun setColor(color: Int) {
        Color.colorToHSV(color, mHsv)
        mAlpha = Color.alpha(color)
        updateValue()
        calculateCursorPosition()
    }

    open fun updateValue() {}

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawContent(canvas)
        drawCursor(canvas)
    }

    abstract fun drawContent(canvas: Canvas?)

    private fun drawCursor(canvas: Canvas?) {
        canvas?.drawCircle(mCursorPosX, mCursorPosY, (mCursorSize - mCursorOffset) / 2F, mCursorPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initRect(w, h, oldw, oldh)
        refreshColorView()
        calculateCursorPosition()
    }

    open fun initRect(w: Int, h: Int, oldw: Int, oldh: Int) {
        var offsetV = getOffsetVertical()
        var offsetH = getOffsetHorizontal()
        mRect.set(offsetH, offsetV, w - offsetH, h - offsetV)
    }

    open fun getOffsetVertical(): Int {
        return 0
    }
    open fun getOffsetHorizontal(): Int {
        return mCursorSize / 2
    }
    abstract fun refreshColorView()
    abstract fun calculateCursorPosition()
}
