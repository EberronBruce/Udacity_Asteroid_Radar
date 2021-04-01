package com.udacity.asteroidradar.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R

class MainAsteriodAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val view = layoutInflator.inflate(R.layout.asteriod_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.setTextColor(Color.WHITE)
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)