package com.bangkit.capstone.meater.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkit.capstone.meater.databinding.ActivityResultBinding
import com.bumptech.glide.Glide

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

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

            val path = Uri.parse(extras.getString(PATH_URI))
            showResult(path)
        }
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
}