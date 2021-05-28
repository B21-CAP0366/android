package com.bangkit.capstone.meater.ui.result

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.meater.util.RecognitionEntity

class ResultViewModel(application: Application) : ViewModel() {

    companion object {
        const val FILE_NAME = "MeatModel.txt"
    }

    private val inputString = application.assets.open(FILE_NAME).bufferedReader().use { it.readText() }
    private val list = inputString.split("\n")

    fun getResult(array: FloatArray): List<RecognitionEntity> {
        val items = ArrayList<RecognitionEntity>()

        for (i in 0..array.size - 1) {
            val percentage = array[i] * 100f
            items.add(
                RecognitionEntity(
                    list[i],
                    "$percentage%"
                )
            )
        }
        return items.sortedByDescending { it.confidence }
    }
}