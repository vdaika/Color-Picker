package com.vdk.color.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

//TODO: make color palette
class ColorPaletteView : ColorView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun drawContent(canvas: Canvas?) {
        TODO("Not yet implemented")
    }

    override fun refreshColorView() {
        TODO("Not yet implemented")
    }

    override fun calculateCursorPosition() {
        TODO("Not yet implemented")
    }
}