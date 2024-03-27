package com.example.clock.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.clock.databinding.ClockItemBinding
import com.example.clock.model.Time

class ClockItemViewHolder(val vb: ClockItemBinding): RecyclerView.ViewHolder(vb.root) {
    fun bindView(item: Time) {
        vb.tvTime.text = item.time
        vb.tvLocation.text = item.location
    }
}