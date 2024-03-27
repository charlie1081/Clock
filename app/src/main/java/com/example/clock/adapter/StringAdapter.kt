package com.example.clock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.clock.databinding.StringItemBinding
import com.example.clock.dialog.ListDialog
import com.example.clock.viewholder.StringItemViewHolder

class StringAdapter(private val listener: StringItemViewHolder.OnItemClickListener, private val dialog: ListDialog? = null) : ListAdapter<String, StringItemViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringItemViewHolder {
        val vb = StringItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return StringItemViewHolder(vb, listener, dialog)
    }

    override fun onBindViewHolder(holder: StringItemViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }
}