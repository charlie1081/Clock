package com.example.clock.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.databinding.CheckboxItemBinding
import com.example.clock.model.CheckboxItem
import com.example.clock.model.Mode

class CheckboxItemViewHolder(private val vb: CheckboxItemBinding) :
    RecyclerView.ViewHolder(vb.root) {
    fun bindView(item: CheckboxItem) {
        vb.tvTimeZone.text = item.timeZone.substring(item.timeZone.indexOf("/") + 1)
        vb.checkbox.isChecked = item.isChecked

        when (item.mode) {
            Mode.Display -> {
                vb.checkbox.isVisible = false
            }

            else -> {
                vb.checkbox.isVisible = true
            }
        }

        vb.checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            item.isChecked = isChecked
        }
    }
}