package com.drawing.canvasd

import android.app.Dialog
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var mImageButtonCurrentPaint:ImageButton?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawing_view=findViewById<DrawingView>(R.id.drawing_view)
        val brush_dialog=findViewById<ImageButton>(R.id.brush_dialog)
        val ll_paint_colors=findViewById<LinearLayout>(R.id.ll_paint_colors)

        mImageButtonCurrentPaint=ll_paint_colors[1] as ImageButton

        mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.color_button_pressed))
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

    fun paintClicked(view: View){
        if (view !=mImageButtonCurrentPaint){
            val imageButton=view as ImageButton
            val colorTag=imageButton.tag.toString()
            val drawing_view=findViewById<DrawingView>(R.id.drawing_view)

            drawing_view.setColor(colorTag)
            imageButton.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.color_button_pressed))
            mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.color_button_unpressed))
            mImageButtonCurrentPaint=view
        }
    }

}
