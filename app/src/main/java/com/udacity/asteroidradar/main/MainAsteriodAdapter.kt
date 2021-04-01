package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class MainAsteriodAdapter: ListAdapter<Asteroid, MainAsteriodAdapter.ViewHolder>(MainAsteriodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeName: TextView = itemView.findViewById(R.id.mainCodeNameTextView)
        val date: TextView = itemView.findViewById(R.id.mainDateTextView)
        val hazardImage: ImageView = itemView.findViewById(R.id.mainHazardImageView)

        fun bind(item: Asteroid) {
            codeName.text = item.codename
            date.text = item.closeApproachDate
            hazardImage.setImageResource(if (item.isPotentiallyHazardous) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflator = LayoutInflater.from(parent.context)
                val view = layoutInflator.inflate(R.layout.asteriod_item_view, parent, false)

                return ViewHolder(view)
            }
        }
    }
}

class MainAsteriodDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

