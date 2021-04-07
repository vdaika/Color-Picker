package com.vdk.colordemo

import android.graphics.Color
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.vdk.color.OnColorChangedListener
import com.vdk.color.layout.ColorLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout = findViewById<RelativeLayout>(R.id.main_layout)
        val colorPickerView = findViewById<ColorLayout>(R.id.color_view)
        colorPickerView.setColor(Color.RED)
        colorPickerView.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(color: Int) {
                layout.setBackgroundColor(color)
            }
        })
    }
}