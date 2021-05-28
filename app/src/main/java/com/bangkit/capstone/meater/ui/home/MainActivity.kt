package com.bangkit.capstone.meater.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.meater.databinding.ActivityMainBinding
import com.bangkit.capstone.meater.ui.camera.CameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floating.setOnClickListener {
            intent = Intent(this@MainActivity, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}