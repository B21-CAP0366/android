package com.bangkit.capstone.meater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.meater.databinding.ActivityMainBinding
import com.bangkit.capstone.meater.ui.CameraActivity
import com.bangkit.capstone.meater.ui.ResultActivity

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