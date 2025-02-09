package com.example.mks.quotes_app_with_pagination_in_kotlin.presentation

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mks.quotes_app_with_pagination_in_kotlin.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream


class ImageActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var btn_save:Button
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image)
        imageView =findViewById(R.id.image_view)
        btn_save=findViewById(R.id.save_btn)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val  intent = intent
        val  imageurl  = intent.getStringExtra("imageurl")

        Glide.with(this).load(imageurl).into(imageView)







        btn_save.setOnClickListener {


            val drawable: Drawable? = imageView.drawable
            val bitmap: Bitmap? = if (drawable is BitmapDrawable) {
                drawable.bitmap
            }
            else {
                    null
            }
           bitmap?.let {
                // Do something with the bitmap, like saving it or using it in another function
                   // savebitmap(this,it)
                savebitmapintoscope_stroage(it)
               Toast.makeText(this,"images added",Toast.LENGTH_LONG).show()

            }




        }





    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun savebitmapintoscope_stroage(bitmap: Bitmap){
      val resolver = contentResolver



      val name = System.currentTimeMillis()
      lifecycleScope.launch(Dispatchers.IO) {
          val imagecollection =
              MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

          val contentvalues  =ContentValues().apply {

             put(MediaStore.Images.Media.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+"/Myimages")
            //image name
            put(MediaStore.Images.Media.DISPLAY_NAME,"$name"+".jpg")
            // meme type
            put(MediaStore.Images.Media.MIME_TYPE,"images/jpg")
            //date
            put(MediaStore.Images.Media.DATE_TAKEN,name)

           put(MediaStore.Images.Media.IS_PENDING,0)

          }
        val uri = resolver.insert(imagecollection,contentvalues)
        uri?.let {
            try {
                resolver.openOutputStream(it).use {

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it!!)
                }
              contentvalues.clear()
              contentvalues.put(MediaStore.Images.Media.IS_PENDING,0)
              resolver.update(it,contentvalues,null,null)


            }
            catch (e:Exception){
                e.stackTrace
               resolver.delete(uri,null,null)

            }

        }



      }
    }








    @RequiresApi(Build.VERSION_CODES.Q)
    fun savebitmap(context : Context, bitmap: Bitmap){
        val resolver = context.contentResolver

        lifecycleScope.launch(Dispatchers.IO) {
            val  imagecollection = MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )
            val timeinmills = System.currentTimeMillis()

            val imagecontentvalues = ContentValues().apply {

                // path of image
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/Latest_images_folder/")
                // name of image
                put(MediaStore.Images.Media.DISPLAY_NAME,"$timeinmills"+".jpg")
                // type of image
                put(MediaStore.Images.Media.MIME_TYPE,"images/jpg")
                // date on which image save
                put(MediaStore.Images.Media.DATE_TAKEN,timeinmills)
                // image save wait
                put(MediaStore.Images.Media.IS_PENDING,1)
            }

            val imageuri = resolver.insert(
                imagecollection,imagecontentvalues
            )
            imageuri.let {
                try {
                    resolver.openOutputStream(imageuri!!).let {
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,it!!)
                    }
                    imagecontentvalues.clear()
                    imagecontentvalues.put(MediaStore.MediaColumns.IS_PENDING,0)
                    resolver.update(
                        imageuri,imagecontentvalues,null,null
                    )
                }
                catch (e:Exception){
                    e.stackTrace
                    resolver.delete(imageuri!!,null,null)

                }

            }



        }

    }


}