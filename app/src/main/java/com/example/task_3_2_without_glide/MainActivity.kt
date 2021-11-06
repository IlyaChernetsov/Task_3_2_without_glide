package com.example.task_3_2_without_glide

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.task_3_2_without_glide.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var bool: Boolean = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showPlaceholder()
        binding.imageView.setOnClickListener {
            if(bool)
                loadImage(binding.editText.text.toString())
            else{err()}
        }
    }


    private fun loadImage(url: String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            // Image URL
            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)
                // Only for making changes in UI
                handler.post {
                    binding.imageView.setImageBitmap(image)
                }


            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Can't load this image, check url", Toast.LENGTH_SHORT).show()
                    binding.imageView.setImageResource(R.drawable.ic_baseline_broken_image_24)
                }
            }
        }

    }

    private fun showPlaceholder() {
        binding.imageView.setImageResource(R.drawable.ic_baseline_image_24)
    }

    private fun err() {
        Toast.makeText(this, "Can't load this image, check url", Toast.LENGTH_SHORT).show()
        binding.imageView.setImageResource(R.drawable.ic_baseline_broken_image_24)
    }
}