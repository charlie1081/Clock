package com.example.clock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.clock.databinding.ClockItemBinding
import com.example.clock.model.Time
import com.example.clock.viewholder.ClockItemViewHolder
import timber.log.Timber

class ClockAdapter : ListAdapter<Time, ClockItemViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Time>() {
            override fun areItemsTheSame(oldItem: Time, newItem: Time): Boolean {
                Timber.d("areItemsTheSame ${oldItem == newItem}")
                return oldItem.timeZone == newItem.timeZone
            }

            override fun areContentsTheSame(oldItem: Time, newItem: Time): Boolean {
                Timber.d("areContentsTheSame ${oldItem == newItem}")
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockItemViewHolder {
        val vb = ClockItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ClockItemViewHolder(vb)
    }

    override fun onBindViewHolder(holder: ClockItemViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}