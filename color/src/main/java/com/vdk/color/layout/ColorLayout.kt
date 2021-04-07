package com.vdk.color.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.vdk.color.OnColorChangedListener
import com.vdk.color.view.ColorOpacityView
import com.vdk.color.view.ColorRectangleView
import com.vdk.color.view.ColorValueView
import com.vdknd.color.color.colordemo.color.R

class ColorLayout : RelativeLayout {
    private lateinit var mColorRectangleView: ColorRectangleView
    private lateinit var mColorValueView: ColorValueView
    private lateinit var mColorOpacityView: ColorOpacityView

    private var mOnColorChangedListener: OnColorChangedListener? = null

    constructor(context: Context?) : super(context) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.color_layout, this);
        mColorRectangleView = findViewById(R.id.color_rectangle_view)
        mColorValueView = findViewById(R.id.color_value_view)
        mColorOpacityView = findViewById(R.id.color_opacity_view)
        mColorRectangleView.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(color: Int) {
                mColorValueView.setColor(color)
                mColorOpacityView.setColor(color)
                mOnColorChangedListener?.onColorChanged(color)
            }
        })
        mColorValueView.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(color: Int) {
                mOnColorChangedListener?.onColorChanged(color)
            }
        })
        mColorOpacityView.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(color: Int) {
                mOnColorChangedListener?.onColorChanged(color)
            }
        })
    }

    fun setOnColorChangedListener(listener: OnColorChangedListener) {
        mOnColorChangedListener = listener
    }

    fun setColor(color: Int) {
        mColorRectangleView.setColor(color)
        mColorValueView.setColor(color)
        mColorOpacityView.setColor(color)
    }
}