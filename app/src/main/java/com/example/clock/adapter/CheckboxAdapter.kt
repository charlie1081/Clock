package com.example.clock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.clock.databinding.CheckboxItemBinding
import com.example.clock.model.CheckboxItem
import com.example.clock.viewholder.CheckboxItemViewHolder
import timber.log.Timber

class CheckboxAdapter : ListAdapter<CheckboxItem, CheckboxItemViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CheckboxItem>() {
            override fun areItemsTheSame(oldItem: CheckboxItem, newItem: CheckboxItem): Boolean {
                Timber.d("areItemsTheSame ${oldItem == newItem}")
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CheckboxItem, newItem: CheckboxItem): Boolean {
                Timber.d("areContentsTheSame ${oldItem == newItem}")
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckboxItemViewHolder {
        val vb = CheckboxItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CheckboxItemViewHolder(vb)
    }

    override fun onBindViewHolder(holder: CheckboxItemViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}