package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class MainAsteriodAdapter: RecyclerView.Adapter<MainAsteriodAdapter.ViewHolder>() {
    var data = listOf<Asteroid>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val view = layoutInflator.inflate(R.layout.asteriod_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.codeName.text = item.codename
        holder.date.text = item.closeApproachDate
        holder.hazardImage.setImageResource( if (item.isPotentiallyHazardous) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeName: TextView = itemView.findViewById(R.id.mainCodeNameTextView)
        val date: TextView = itemView.findViewById(R.id.mainDateTextView)
        val hazardImage: ImageView = itemView.findViewById(R.id.mainHazardImageView)
    }
}

