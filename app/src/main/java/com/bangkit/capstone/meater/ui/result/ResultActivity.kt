package com.bangkit.capstone.meater.ui.result

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.meater.databinding.ActivityResultBinding
import com.bangkit.capstone.meater.ml.MeatModel
import com.bangkit.capstone.meater.util.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ResultActivity : AppCompatActivity() {

    private lateinit var activityResultBinding: ActivityResultBinding
    private lateinit var bitmap: Bitmap
    private lateinit var resultViewModel: ResultViewModel
    private lateinit var resultAdapter: ResultAdapter

    companion object {
        const val PATH_URI = "path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        setSupportActionBar(activityResultBinding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(application)
        resultViewModel = ViewModelProvider(this, factory).get(ResultViewModel::class.java)

        resultAdapter = ResultAdapter()

        isLoading(true)
        //get bundle from Camera Activity
        val extras = intent.extras
        if (extras != null) {
            convertURItoBitmap(extras.getString(PATH_URI))
            isLoading(false)

        }

        BottomSheetBehavior.from(activityResultBinding.bottomSheet).apply {
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            this.setPeekHeight(550)
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            activityResultBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityResultBinding.progressBar.visibility = View.GONE
        }
    }

    @Suppress("DEPRECATION")
    private fun convertURItoBitmap(path: String?) {

        val uri: Uri? = Uri.parse(path)

        if (uri != null) {
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }

        Glide.with(this@ResultActivity)
            .load(uri)
            .into(activityResultBinding.imgResult)

        predict(this)
    }

    private fun predict(context: Context) {
        val model = MeatModel.newInstance(context)

        //resizing current Bitmap
        val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)

        val tensorBuffer = TensorImage.fromBitmap(resized)

        val byteBuffer = TensorImage.createFrom(tensorBuffer, DataType.FLOAT32).buffer

        //resetting Buffer
        byteBuffer.rewind()

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        //Calling Result from ViewModel
        val predictResult = resultViewModel.getResult(outputFeature0.floatArray)
        resultAdapter.setResult(predictResult)

        showRecyclerView()

        // Releases model resources if no longer used.
        model.close()
    }

    private fun showRecyclerView() {
        with(activityResultBinding.rvResult) {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            setHasFixedSize(true)
            adapter = resultAdapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}