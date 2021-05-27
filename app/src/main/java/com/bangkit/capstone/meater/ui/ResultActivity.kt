package com.bangkit.capstone.meater.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.bangkit.capstone.meater.databinding.ActivityResultBinding
import com.bangkit.capstone.meater.ml.MeatModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var bitmap: Bitmap

    companion object {
        const val PATH_URI = "path"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLoading(true)
        //get bundle from Camera Activity
        val extras = intent.extras
        if (extras != null) {
            isLoading(false)

            convertURItoBitmap(extras.getString(PATH_URI))

            //val path = Uri.parse(extras.getString(PATH_URI))
            //showResult(path)
        }

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        //predict(this)

        //var resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true)
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showResult(path: Uri?) {
        Glide.with(this@ResultActivity)
            .load(path)
            .into(binding.imgResult)
    }

    @Suppress("DEPRECATION")
    private fun convertURItoBitmap(path: String?) {

        val uri: Uri? = Uri.parse(path)

        if (uri != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }

        Glide.with(this@ResultActivity)
            .load(uri)
            .into(binding.imgResult)

        predict(this)
    }

    private fun predict(context: Context) {
        val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true)
        val model = MeatModel.newInstance(context)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)

        //val tensorBuffer = TensorImage.fromBitmap(resized)
        val byteBuffer = TensorImage.fromBitmap(resized).buffer
        //val byteBuffer = tensorBuffer.buffer

        byteBuffer.rewind()

        Log.d("TAG", "byteBuffer value: ${byteBuffer.get()}, ${byteBuffer.limit()}, $byteBuffer.")
        Log.d("TAG", "inputFeature0 Value: ${inputFeature0.buffer.get()}, ${inputFeature0.buffer.limit()} , shape Get 0: ${inputFeature0.shape.get(0)}, shape Get 1: ${inputFeature0.shape.get(1)}, shape Get 2: ${inputFeature0.shape.get(2)}, shape Get 3: ${inputFeature0.shape.get(3)}, shape size: ${inputFeature0.shape.size}")

        //still error
        //inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        //binding.tvResult.setText(outputFeature0.floatArray[1].toString())

        // Releases model resources if no longer used.
        model.close()
    }
}