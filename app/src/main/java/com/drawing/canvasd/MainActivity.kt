package com.drawing.canvasd

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawing_view=findViewById<DrawingView>(R.id.drawing_view)
        val brush_dialog=findViewById<ImageButton>(R.id.brush_dialog)

        drawing_view.setSizeForBrush(20.toFloat())
        brush_dialog.setOnClickListener{
            showBrushSizeChooserDialog()
        }

    }
    private fun showBrushSizeChooserDialog(){
        val brush_dialog= Dialog(this)
        brush_dialog.setContentView(R.layout.brush_size_layout)
        brush_dialog.setTitle("Brush Size: ")
        val small_brush=brush_dialog.findViewById<ImageButton>(R.id.brush_small)
        val medium_brush=brush_dialog.findViewById<ImageButton>(R.id.brush_medium)
        val large_brush=brush_dialog.findViewById<ImageButton>(R.id.brush_large)

        small_brush.setOnClickListener{
            val drawing_view=findViewById<DrawingView>(R.id.drawing_view)
            drawing_view.setSizeForBrush(10.toFloat())
            brush_dialog.dismiss()
        }

        medium_brush.setOnClickListener{
            val drawing_view=findViewById<DrawingView>(R.id.drawing_view)
            drawing_view.setSizeForBrush(20.toFloat())
            brush_dialog.dismiss()
        }

        large_brush.setOnClickListener{
            val drawing_view=findViewById<DrawingView>(R.id.drawing_view)
            drawing_view.setSizeForBrush(30.toFloat())
            brush_dialog.dismiss()
        }
        brush_dialog.show()

    }

}
