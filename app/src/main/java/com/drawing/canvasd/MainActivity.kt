package com.drawing.canvasd

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
        val ib_gallery=findViewById<ImageView>(R.id.ib_gallery)

        mImageButtonCurrentPaint=ll_paint_colors[1] as ImageButton

        mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.color_button_pressed))
        drawing_view.setSizeForBrush(20.toFloat())

        brush_dialog.setOnClickListener{
            showBrushSizeChooserDialog()
        }

        ib_gallery.setOnClickListener{
            if (isReadStorageAllowed()){
                val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoIntent,GALLERY)
            }
            else{
                requestStoragePermission()
            }
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

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
            Toast.makeText(this,"Need permission to add a background",Toast.LENGTH_SHORT).show()
        }
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(this,"Permission granted for gallery",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"You just denied the permission",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isReadStorageAllowed():Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode== GALLERY){
                try {
                    if (data!!.data!=null){
                        val iv_background=findViewById<ImageView>(R.id.iv_background)

                        iv_background.setImageURI(data.data)
                    }
                    else{
                        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e:Exception){e.printStackTrace()}
            }
        }
    }

    companion object{
        private const val STORAGE_PERMISSION_CODE =1
        private const val GALLERY =2
    }

}
