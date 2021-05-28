package com.bangkit.capstone.meater.ui.result

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.meater.databinding.ItemResultBinding
import com.bangkit.capstone.meater.util.RecognitionEntity

class ResultAdapter() : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    private var listResult = ArrayList<RecognitionEntity>()

    fun setResult(result: List<RecognitionEntity>?) {
        if (result == null) return
        this.listResult.clear()
        this.listResult.addAll(result)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemResultBinding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(itemResultBinding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(listResult[position])
    }

    override fun getItemCount(): Int {
        return listResult.size
        Log.d("TAG", "listResult.size: $itemCount")
        Log.d("TAG", "listResult.size 2nd: ${listResult.size}")
    }

    class ResultViewHolder(private val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(result: RecognitionEntity){
            with(binding) {
                tvResult.setText(result.label)
                tvPercentage.setText(result.confidence)
            }
        }

    }
}