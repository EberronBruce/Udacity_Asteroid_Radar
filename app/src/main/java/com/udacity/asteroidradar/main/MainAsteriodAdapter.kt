package com.udacity.asteroidradar.main

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAsteriodAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)